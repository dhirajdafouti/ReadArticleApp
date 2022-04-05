package com.project.readarticleapp.data.network.networkModels.mapper

import com.project.readarticleapp.data.database.ArticleEntity
import com.project.readarticleapp.data.network.networkModels.RemoteArticleItem
import org.json.JSONObject
import org.json.JSONArray
import org.json.JSONException
import timber.log.Timber


data class NetworkArticleData(val remoteArticleData: List<RemoteArticleItem>)

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

//The Article Data received from server is parsed.

fun parseRemoteArticleJsonToResult(jsonResult: JSONArray): ArrayList<RemoteArticleItem> {
    val remoteArticleData = ArrayList<RemoteArticleItem>()
    try {
        for (i in 0 until jsonResult.length()) {
            val jsonObject: JSONObject = jsonResult.getJSONObject(i)
            val id = jsonObject.optInt("id")
            val title = jsonObject.optString("title")
            val url = jsonObject.optString("url")
            val imageUrl = jsonObject.optString("imageUrl")
            val newsSite = jsonObject.optString("newsSite")
            val publishedAt = jsonObject.optString("publishedAt")
            val summary = jsonObject.optString("summary")
            val updatedAt = jsonObject.optString("updatedAt")
            val featured = jsonObject.getBoolean("featured")
            remoteArticleData.add(RemoteArticleItem(featured = featured,
                id = id,
                imageUrl = imageUrl,
                newsSite = newsSite,
                publishedAt = publishedAt,
                summary = summary,
                updatedAt = updatedAt,
                url = url,
                title = title))

        }
    } catch (e: JSONException) {
        Timber.d("Json Exception Received...")
    }
    return remoteArticleData
}
