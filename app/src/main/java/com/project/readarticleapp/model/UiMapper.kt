package com.project.readarticleapp.model

import com.project.readarticleapp.data.database.ArticleEntity

data class DataBaseArticleData(val articleDataList: List<ArticleEntity>)

data class DataBaseArticleDetailsData(val articleData: ArticleEntity)

//The function will be a mapper from the database object to the ui object.

fun DataBaseArticleDetailsData.asArticleDetailsUiDataModel(): ArticleModelDetails {
    return ArticleModelDetails(
        id = articleData.id,
        featured = articleData.featured,
        publishedAt = articleData.publishedAt,
        summary = articleData.summary,
        title = articleData.title,
        updatedAt = articleData.updatedAt,
        url = articleData.url

    )
}

fun DataBaseArticleData.asArticleUiDataModel(): List<ArticleModelList> {
    return articleDataList.map {
        ArticleModelList(
            id = it.id,
            imageUrl = it.imageUrl,
            site = it.newsSite,
            title = it.title,
            summary = it.summary
        )
    }
}