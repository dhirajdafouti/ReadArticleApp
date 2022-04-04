package com.project.readarticleapp.model

import com.squareup.moshi.Json

data class ArticleModelDetails(
    val featured: Boolean,
    val id: Int,
    val publishedAt: String,
    val summary: String,
    val title: String,
    val updatedAt: String,
    val url: String,
)