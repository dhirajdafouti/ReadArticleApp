package com.project.readarticleapp.data.database

import androidx.room.Database


@Database(entities = [ArticleDao::class], version = 1)
abstract class ArticleDataBase {
    abstract fun getArticlesDao(): ArticleDao
}