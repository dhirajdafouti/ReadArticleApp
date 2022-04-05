package com.project.readarticleapp.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
@ExperimentalCoroutinesApi

class ArticleDaoTest : TestCase() {

    private lateinit var articleDataBase: ArticleDataBase

    //We have added this rule to swap the background executor. And this new executor will work synchronously.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @ExperimentalCoroutinesApi
    @Test
    fun insertAndQueryArticleData() = runBlocking<Unit> {
        //Information is stored in the Room database memory.
        //InMemoryDatabaseBuilder method helps us to create an in-memory room database.
        // Instance while allowMainThreadQueries() helps us to run the database queries on the main thread.
        articleDataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArticleDataBase::class.java
        ).allowMainThreadQueries().build()
        val articleList = dummyList()

        articleDataBase.getArticlesDao().insertArticles(articleList)
        val articleData = articleDataBase.getArticlesDao().getArticlesFromDataBase()
        assertEquals(articleData.get(2).featured, true)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun insertAndQueryArctielDataWithId() = runBlocking<Unit> {
        //Information is stored in the Room database memory.
        //InMemoryDatabaseBuilder method helps us to create an in-memory room database.
        // Instance while allowMainThreadQueries() helps us to run the database queries on the main thread.
        articleDataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArticleDataBase::class.java
        ).allowMainThreadQueries().build()
        val articleList = dummyList()

        articleDataBase.getArticlesDao().insertArticles(articleList)

        val articleDataWithId =
            articleDataBase.getArticlesDao().getArticleWithId(2)
        assertThat(articleDataWithId)
        assertThat(articleDataWithId.id.compareTo(2))

    }

    private fun dummyList(): List<ArticleEntity> {
        val articleList = listOf(
            ArticleEntity(1,
                true,
                "movie1_sub_title1",
                "NEW1",
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
            ArticleEntity(6,
                true,
                "movie5_sub_title1",
                "NEW4",
                "SUMMARY",
                "TILE",
                "UPDATED",
                "PUBLISHED",
                "updated"),
        )
        return articleList
    }
}

