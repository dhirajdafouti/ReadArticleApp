package com.project.readarticleapp.data.network.networkModels.result

import com.project.readarticleapp.model.ArticleModelDetails
import com.project.readarticleapp.model.ArticleModelList

sealed class ArticleResult {

    data class Success(val data: List<ArticleModelList>) : ArticleResult()
    data class Error(val error: String, val code: String) : ArticleResult()
}

sealed class ArticleDetailsResult {

    data class Success(val data: List<ArticleModelDetails>) : ArticleDetailsResult()
    data class Error(val error: String, val code: String) : ArticleDetailsResult()
}

