package com.project.readarticleapp.data.network.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
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
import retrofit2.converter.moshi.MoshiConverterFactory

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
        instantTaskExecutorRule = InstantTaskExecutorRule()
        mockWebService = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebService.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshiBuilder))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(ArticleService::class.java)
    }

    @Test
    fun getMovieOfferTest() = runBlocking {
        enqueueResponse("movie_offers.json")
        val movieOffer = service.getArticles().body()
        Assert.assertNotNull(movieOffer)

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
    fun stopService() {
        mockWebService.shutdown()
    }
}