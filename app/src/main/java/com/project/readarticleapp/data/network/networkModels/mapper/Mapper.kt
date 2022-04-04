package com.project.readarticleapp.data.network.networkModels.mapper

import com.project.readarticleapp.data.database.ArticleEntity

data class NetworkArticleData(val remoteArticleData: List<ArticleEntity>)

//The function will be a mapper from the Remote data objects to the database objects.
fun NetworkArticleData.asArticleDataBaseModel(): List<ArticleEntity> {
    return remoteArticleData.map {
        ArticleEntity(
            id = it.id,
            featured = it.featured,
            imageUrl = it.imageUrl,
            newsSite = it.newsSite,
            publishedAt = it.publishedAt,
            summary = it.summary,
            title = it.title,
            updatedAt = it.updatedAt,
            url = it.url

        )
    }
}
