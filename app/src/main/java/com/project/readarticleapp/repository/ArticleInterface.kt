package com.project.readarticleapp.repository

import com.google.gson.JsonObject
import com.project.readarticleapp.data.database.ArticleEntity
import com.project.readarticleapp.data.network.networkModels.RemoteArticleData
import retrofit2.Response

interface ArticleInterface {
    suspend fun getArticlesFromRemoteServer(): Response<String>
    suspend fun saveArticlesDataToDataBase(articleData: List<ArticleEntity>)

    //TODO:The functionality of the application can be extended with fetching the Article with Item Id from Remote Server.
    suspend fun getArticleWithIdFromRemoteServer(itemId: Int): Response<String>

    //TODO:The functionality of the application can be extended with saving the Article data with Item Id onto the database.
    suspend fun saveArticleDataToDataBaseWithItemId(articleData: ArticleEntity)

    fun getArticleDataListFromDataBase(): List<ArticleEntity>

    fun getArticleDataDetailsFromDataBase(itemId: Int): ArticleEntity
}