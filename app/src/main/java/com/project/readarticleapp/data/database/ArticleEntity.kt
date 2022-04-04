package com.project.readarticleapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "article_table")
data class ArticleEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "featured")
    val featured: Boolean,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,

    @ColumnInfo(name = "newsSite")
    val newsSite: String,

    @ColumnInfo(name = "publishedAt")
    val publishedAt: String,

    @ColumnInfo(name = "summary")
    val summary: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "updatedAt")
    val updatedAt: String,

    @ColumnInfo(name = "url")
    val url: String,

    )
