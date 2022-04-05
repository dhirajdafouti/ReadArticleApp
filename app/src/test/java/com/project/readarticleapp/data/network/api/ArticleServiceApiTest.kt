package com.project.readarticleapp.data.network.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.project.readarticleapp.data.network.networkModels.RemoteArticleItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

@RunWith(JUnit4::class)
class ArticleServiceApiTest {
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
        instantTaskExecutorRule = InstantTaskExecutorRule()
        mockWebService = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebService.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshiBuilder))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(ArticleService::class.java)
    }

    @Test
    fun getArticleDataTest() = runBlocking {
        enqueueResponse("articles.json")
        val articleData = service.getArticles().body()
        Assert.assertNotNull(articleData)
        Assert.assertEquals(articleData?.length, 10)
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


    //The Article Data received from server is parsed.

    fun parseRemoteArticleJsonToResult(jsonResult: JSONArray): ArrayList<RemoteArticleItem> {
        val remoteArticleData = ArrayList<RemoteArticleItem>()
        try {
            for (i in 0 until jsonResult.length()) {
                val jsonObject: JSONObject = jsonResult.getJSONObject(i)
                val id = jsonObject.optInt("id")
                val title = jsonObject.optString("title")
                val url = jsonObject.optString("url")
                val imageUrl = jsonObject.optString("imageUrl")
                val newsSite = jsonObject.optString("newsSite")
                val publishedAt = jsonObject.optString("publishedAt")
                val summary = jsonObject.optString("summary")
                val updatedAt = jsonObject.optString("updatedAt")
                val featured = jsonObject.getBoolean("featured")
                remoteArticleData.add(RemoteArticleItem(featured = featured,
                    id = id,
                    imageUrl = imageUrl,
                    newsSite = newsSite,
                    publishedAt = publishedAt,
                    summary = summary,
                    updatedAt = updatedAt,
                    url = url,
                    title = title))

            }
        } catch (e: JSONException) {
            Timber.d("Json Exception Received...")
        }
        return remoteArticleData
    }

    @Test
    fun getArticleDataWithIdTest() = runBlocking {
        enqueueResponse("movie_data.json")

        val movieData = service.getArticlesWithArticleId(1).body()
        Assert.assertNotNull(movieData)
        Assert.assertEquals(movieData?.remoteArticleData?.size, 10)
    }

    @After
    fun stopService() {
        mockWebService.shutdown()
    }
}