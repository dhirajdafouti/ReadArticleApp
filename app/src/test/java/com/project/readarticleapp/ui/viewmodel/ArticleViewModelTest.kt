package com.project.readarticleapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.core.app.ApplicationProvider
import com.project.readarticleapp.data.database.ArticleDao
import com.project.readarticleapp.data.database.ArticleDataBase
import com.project.readarticleapp.data.database.ArticleEntity
import com.project.readarticleapp.repository.BaseRepository
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * As we are running an instrumented unit test here; and it requires a real device or emulator. And you know it is time taking to do things on a real device or emulator. So to make this process faster, we can use Roboelectric.

Roboelectric can simulate Android Environment in JVM only. And with it, you do not need an emulator or real device to run tests. And that is why it will make your tests very fast.
 */
@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class ArticleViewModelTest : TestCase() {

    private lateinit var viewModel: ArticleViewModel

    private lateinit var mBaseRepository: BaseRepository

    private lateinit var mDataBase: ArticleDataBase

    private lateinit var mDao: ArticleDao

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()

    @get :Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        mBaseRepository = BaseRepository()
        mBaseRepository.setUp()
        viewModel = ArticleViewModel(mBaseRepository)
        mDataBase = inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
            ArticleDataBase::class.java).allowMainThreadQueries().build()
        mDao = mDataBase.getArticlesDao()

    }

    @Test
    fun `setting the Article Id with Integer Value, returns livedata observer`() {
        viewModel.setArticleId(1)
        val movieId = viewModel.articleValueToLiveData.getOrAwaitValueTest()
        Assert.assertEquals(1, movieId)

    }

    //TOD0:
    @Test
    fun `fetching article details from article data base with id, return article details`() =
        runBlocking {
            mBaseRepository.saveArticlesDataToDataBase(mutableListOf(ArticleEntity(6,
                true,
                "movie1_sub_title1",
                "NEW1",
                "SUMMARY",
                "TILE",
                "UPDATED",
                "PUBLISHED",
                "updated"), ArticleEntity(6,
                true,
                "movie1_sub_title1",
                "NEW1",
                "SUMMARY",
                "TILE",
                "UPDATED",
                "PUBLISHED",
                "updated")))

            viewModel.getArticleDetails(1)
            val articleId = viewModel.articleDetailsData.getOrAwaitValueTest()
            Assert.assertNotNull(articleId)

        }


    @After
    public override fun tearDown() {
        mBaseRepository.tearDown()
    }
}