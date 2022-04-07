package com.project.readarticleapp.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.project.readarticleapp.data.database.ArticleDataBase
import com.project.readarticleapp.data.database.ArticleEntity
import com.project.readarticleapp.data.network.api.ArticleService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


open class BaseRepository : ArticleInterface {

    private lateinit var service: ArticleService


    private lateinit var mockWebService: MockWebServer


    fun setUp() {
        mockWebService = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebService.url("/"))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(ArticleService::class.java)

    }


    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebService.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }

    public fun tearDown() {
        mockWebService.shutdown()
    }

    override suspend fun getArticlesFromRemoteServer(): Response<String> {
        return service.getArticles()
    }

    override suspend fun saveArticlesDataToDataBase(articleData: List<ArticleEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun getArticleWithIdFromRemoteServer(itemId: Int): Response<String> {
        TODO("Not yet implemented")
    }

    override suspend fun saveArticleDataToDataBaseWithItemId(articleData: ArticleEntity) {
        TODO("Not yet implemented")
    }

    override fun getArticleDataListFromDataBase(): List<ArticleEntity> {
        TODO("Not yet implemented")
    }

    override fun getArticleDataDetailsFromDataBase(itemId: Int): ArticleEntity {
        TODO("Not yet implemented")
    }

}