package com.project.readarticleapp.data.network.networkModels

data class RemoteArticleData(
    val remoteArticleData: ArrayList<RemoteArticleItem>,
)

data class RemoteArticleItem(
    val featured: Boolean,

    val id: Int,

    val imageUrl: String,

    val newsSite: String,

    val publishedAt: String,

    val summary: String,

    val title: String,

    val updatedAt: String,

    val url: String,
)
