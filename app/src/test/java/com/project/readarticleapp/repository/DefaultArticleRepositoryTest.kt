package com.project.readarticleapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.project.readarticleapp.data.network.networkModels.mapper.parseRemoteArticleJsonToResult
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class DefaultMovieRepositoryTest : TestCase() {

    private lateinit var mBaseRepository: BaseRepository

    @Rule
    lateinit var instantTaskExecutorRule: InstantTaskExecutorRule

    @Before
    public override fun setUp() {
        super.setUp()
        instantTaskExecutorRule = InstantTaskExecutorRule()
        mBaseRepository = BaseRepository()
        mBaseRepository.setUp()

    }

    @Test
    fun testMovieOfferListFromServer() = runBlocking {


    }


    @After
    public override fun tearDown() {
        mBaseRepository.tearDown()
        super.tearDown()
    }
}