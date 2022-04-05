package com.project.readarticleapp.data.network.networkModels

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json

data class RemoteArticleData(
    val remoteArticleData: List<RemoteArticleItem>,
)

data class RemoteArticleItem(
    @Json(name = "featured") val featured: Boolean,

    @Json(name = "events") val events: List<Any>,

    @Json(name = "launches") val launches: List<Any>,
    @Json(name = "id") val id: Int,

    @Json(name = "imageUrl") val imageUrl: String,

    @Json(name = "newsSite") val newsSite: String,

    @Json(name = "publishedAt") val publishedAt: String,

    @Json(name = "summary") val summary: String,

    @Json(name = "title") val title: String,

    @Json(name = "updatedAt") val updatedAt: String,

    @Json(name = "url") val url: String,
)
