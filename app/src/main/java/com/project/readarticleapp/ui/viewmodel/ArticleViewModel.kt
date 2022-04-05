package com.project.readarticleapp.ui.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.project.readarticleapp.data.network.networkModels.mapper.NetworkArticleData
import com.project.readarticleapp.data.network.networkModels.mapper.asArticleDataBaseModel
import com.project.readarticleapp.data.network.networkModels.mapper.parseRemoteArticleJsonToResult
import com.project.readarticleapp.data.network.networkModels.result.ArticleDetailsResult
import com.project.readarticleapp.data.network.networkModels.result.ArticleResult
import com.project.readarticleapp.model.DataBaseArticleData
import com.project.readarticleapp.model.DataBaseArticleDetailsData
import com.project.readarticleapp.model.asArticleDetailsUiDataModel
import com.project.readarticleapp.model.asArticleUiDataModel
import com.project.readarticleapp.repository.ArticleInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleRepoInterface: ArticleInterface,
) : ViewModel() {

    private var articleId: Int = 0

    private val _progress: MutableLiveData<Boolean> = MutableLiveData(false)
    val progressBar: LiveData<Boolean>
        get() = _progress

    private val _articleListData: MutableLiveData<ArticleResult> = MutableLiveData()
    val articleListData: LiveData<ArticleResult>
        get() = _articleListData


    private val _articleDetailsData: MutableLiveData<ArticleDetailsResult> = MutableLiveData()
    val articleDetailsData: LiveData<ArticleDetailsResult>
        get() = _articleDetailsData


    private val _articleIdValue = MutableLiveData<Int>()

    val articleValueToLiveData: LiveData<Int>
        get() = _articleIdValue


    fun getArticles() {
        viewModelScope.launch {
            _progress.postValue(true)
            withContext((Dispatchers.IO)) {
                try {
                    val result = articleRepoInterface.getArticlesFromRemoteServer()
                    val isSuccess = result.isSuccessful
                    val code = result.code()
                    val response = result.body()
                    if (isSuccess && code.toString() == HTTP_RESPONSE_SUCCESS) {
                        val jsonArray = JSONArray(result.body())
                        articleRepoInterface.saveArticlesDataToDataBase(
                            NetworkArticleData(
                                parseRemoteArticleJsonToResult(jsonArray)).asArticleDataBaseModel()
                        )
                        val databaseItems = articleRepoInterface.getArticleDataListFromDataBase()
                        _articleListData.postValue(ArticleResult.Success(DataBaseArticleData(
                            databaseItems).asArticleUiDataModel()))
                        _progress.postValue(false)
                    } else {
                        when (code.toString()) {
                            "403" -> ArticleResult.Error(HTTP_ERROR_RESOURCE_FORBIDDEN,
                                response.toString())
                            "404" -> ArticleResult.Error(HTTP_ERROR_RESOURCE_NOT_FOUND,
                                response.toString())
                            "500" -> ArticleResult.Error(HTTP_ERROR_RESOURCE_SERVERERROR,
                                response.toString())
                            "502" -> ArticleResult.Error(HTTP_ERROR_RESOURCE_BAD_GATEWAY,
                                response.toString())
                            "301" -> ArticleResult.Error(HTTP_ERROR_RESOURCE_REMOVED_RESOURCE_FOUND,
                                response.toString())
                            "302" -> ArticleResult.Error(HTTP_ERROR_RESOURCE_RESOURCE_REMOVED,
                                response.toString())
                            else -> ArticleResult.Error(EXCEPTION,
                                response.toString())
                        }
                        _progress.postValue(false)
                    }

                } catch (error: IOException) {
                    ArticleResult.Error(EXCEPTION,
                        error.toString())
                }
            }
        }
    }

    fun getArticleDetails(id: Int) {
        _progress.postValue(true)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val articleDetails = articleRepoInterface.getArticleDataDetailsFromDataBase(id)
                _articleDetailsData.postValue(ArticleDetailsResult.Success(
                    DataBaseArticleDetailsData(articleDetails).asArticleDetailsUiDataModel()))
                _progress.postValue(false)
            }

        }
    }

    //TODO:This function is exposed to extend the feature for the application.
    fun getArticleWithIdFromServer(id: Int) {
        viewModelScope.launch {
            withContext((Dispatchers.IO)) {
                articleRepoInterface.getArticleWithIdFromRemoteServer(id)
            }
        }
    }


    fun getItemId(): Int {
        return this@ArticleViewModel.articleId
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun clearArticleIdLiveData() {
        _articleIdValue.value = null
    }

    fun setArticleId(movieId: Int) {
        _articleIdValue.value = movieId
        setItemId(movieId)
    }


    fun setItemId(id: Int) {
        this@ArticleViewModel.articleId = id
    }

    companion object {
        private const val HTTP_RESPONSE_SUCCESS = "200"
        private const val HTTP_ERROR_RESOURCE_FORBIDDEN = "403"
        private const val HTTP_ERROR_RESOURCE_NOT_FOUND = "404"
        private const val HTTP_ERROR_RESOURCE_SERVERERROR = "500"
        private const val HTTP_ERROR_RESOURCE_BAD_GATEWAY = "502"
        private const val HTTP_ERROR_RESOURCE_RESOURCE_REMOVED = "301"
        private const val HTTP_ERROR_RESOURCE_REMOVED_RESOURCE_FOUND = "302"
        private const val EXCEPTION = "UnKnown Exception Caught!!"
    }
}