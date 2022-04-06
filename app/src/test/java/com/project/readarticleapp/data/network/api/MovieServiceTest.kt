package com.project.readarticleapp.data.network.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.project.readarticleapp.data.network.networkModels.mapper.NetworkArticleData
import com.project.readarticleapp.data.network.networkModels.mapper.asArticleDataBaseModel
import com.project.readarticleapp.data.network.networkModels.mapper.parseRemoteArticleJsonToResult
import com.project.readarticleapp.model.DataBaseArticleDetailsData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import junit.framework.TestCase
import org.json.JSONArray
import retrofit2.converter.scalars.ScalarsConverterFactory


@RunWith(JUnit4::class)
class MovieServiceTest {
    /**
     * InstantTaskExecutorRule is a JUnit Test Rule that swaps the background executor used
     * by the Architecture Components with a different one which executes each task synchronously.
     */
    @Rule
    lateinit var instantTaskExecutorRule: InstantTaskExecutorRule
    private lateinit var service: ArticleService

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

    @Test
    fun getArticleListTest() = runBlocking {
        enqueueResponse("articles.json")
        val result=service.getArticles().body()
        val jsonArray = JSONArray(result)

        Assert.assertNotNull(NetworkArticleData(
            parseRemoteArticleJsonToResult(jsonArray)).asArticleDataBaseModel())

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

    //TODO:
    @Test
    fun getArticleListWithId() = runBlocking {

    }


    @After
    fun stopService() {
        mockWebService.shutdown()
    }

}