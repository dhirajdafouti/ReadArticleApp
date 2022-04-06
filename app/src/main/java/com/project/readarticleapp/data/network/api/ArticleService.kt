package com.project.readarticleapp.data.network.api


import com.project.readarticleapp.data.network.networkModels.RemoteArticleData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleService {

    //https://api.spaceflightnewsapi.net/v3/articles?_limit=100&_sort=id
    @GET("v3/articles?_limit=100&_sort=id")
    suspend fun getArticles(): Response<String>

    //TODO:The functionality of the application  can be extended with Limit parameter.
    //https://api.spaceflightnewsapi.net/v3/articles?_limit=100
    @GET("v3/articles")
    suspend fun getArticlesWithLimit(
        @Query("_limit") limit: Int,
    ): Response<RemoteArticleData>

    //TODO:The functionality of the application  can be extended with Sort Order.
    //https://api.spaceflightnewsapi.net/v3/articles?_sort=id
    @GET("v3/articles?_sort=id")
    suspend fun getArticlesWithSortOrder(): Response<RemoteArticleData>

    //TODO:The functionality of the application can be extended with fetching the Article with Item Id
    //Article with id..
    //https://api.spaceflightnewsapi.net/v3/articles/{id}
    @GET("v3/articles")
    suspend fun getArticlesWithArticleId(@Query("id") id: Int): Response<RemoteArticleData>
}
