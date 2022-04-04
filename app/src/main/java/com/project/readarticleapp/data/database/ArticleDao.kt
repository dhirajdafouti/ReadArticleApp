package com.project.readarticleapp.data.database

import androidx.room.*

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articleEntity: List<ArticleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticlesWithId(articleEntity: ArticleEntity)

    @Query("Select *FROM article_table")
    fun getArticlesFromDataBase(): List<ArticleEntity>

    @Query("Select *FROM article_table WHERE :id=id")
    fun getArticleWithId(id: Int):ArticleEntity

    //TODO:The functionality of the application  can be extended with Sort Order.
    //The Items from the database can be fetched with sort order id.
    @Query("Select *FROM article_table ORDER BY id DESC")
    fun getArticleWithSortedId()

    //TODO:The functionality of the application  can be extended with deleting the article item with id.
    @Query("DELETE FROM article_table WHERE :id=id")
    fun deleteArticleTable(id: Int)

    @Delete
    fun delete(articleEntity: List<ArticleEntity>)
}