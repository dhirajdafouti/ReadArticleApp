package com.project.readarticleapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.project.readarticleapp.data.database.ArticleEntity
import com.project.readarticleapp.data.network.api.ArticleService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


@RunWith(JUnit4::class)

class DefaultMovieRepositoryTest : TestCase(), ArticleInterface {

    @Rule
    lateinit var instantTaskExecutorRule: InstantTaskExecutorRule
    private lateinit var service: ArticleService

    private val saveArticleList = mutableListOf<ArticleEntity>()

    private lateinit var mockWebService: MockWebServer
    private val moshiBuilder = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Before
    fun createService() {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()
        instantTaskExecutorRule = InstantTaskExecutorRule()
        mockWebService = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebService.url("/"))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ArticleService::class.java)
    }

    //TODO:
    @Test
    fun `execute article list api, store to database , return from database`() = runBlocking {

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

    @After
    public override fun tearDown() {
        mockWebService.shutdown()
        super.tearDown()
    }

    private fun dummyList(): List<ArticleEntity> {
        val articleList = listOf(
            ArticleEntity(6,
                true,
                "movie1_sub_title1",
                "NEW1",
                "SUMMARY",
                "TILE",
                "UPDATED",
                "PUBLISHED",
                "updated"),
            ArticleEntity(8,
                true,
                "movie2_sub_title1",
                "NEW2",
                "SUMMARY",
                "TILE",
                "UPDATED",
                "PUBLISHED",
                "updated"),
            ArticleEntity(2,
                true,
                "movie2_sub_title1",
                "NEW2",
                "SUMMARY",
                "TILE",
                "UPDATED",
                "PUBLISHED",
                "updated"),
            ArticleEntity(4,
                true,
                "movie3_sub_title1",
                "NEW3",
                "SUMMARY",
                "TILE",
                "UPDATED",
                "PUBLISHED",
                "updated"),
            ArticleEntity(1,
                false,
                "movie5_sub_title1",
                "NEW4",
                "SUMMARY",
                "TILE_1_SORTED",
                "UPDATED",
                "PUBLISHED",
                "updated"),
        )
        return articleList
    }

    override suspend fun getArticlesFromRemoteServer(): Response<String> {
        return service.getArticles()
    }

    override suspend fun saveArticlesDataToDataBase(articleData: List<ArticleEntity>) {
        saveArticleList.addAll(articleData)
    }

    override suspend fun getArticleWithIdFromRemoteServer(itemId: Int): Response<String> {
        TODO("Not yet implemented")
    }

    override suspend fun saveArticleDataToDataBaseWithItemId(articleData: ArticleEntity) {
        TODO("Not yet implemented")
    }

    override fun getArticleDataListFromDataBase(): List<ArticleEntity> {
        return saveArticleList
    }

    override fun getArticleDataDetailsFromDataBase(itemId: Int): ArticleEntity {
        return saveArticleList.get(0)
    }
}