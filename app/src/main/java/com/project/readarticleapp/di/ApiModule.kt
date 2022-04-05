package com.project.readarticleapp.di

import com.project.readarticleapp.data.network.api.ArticleService
import com.project.readarticleapp.data.network.api.createNetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun providesNetworkClient(): Retrofit = createNetworkClient(BASE_URL)

    @Singleton
    @Provides
    fun providesArticleService(retrofit: Retrofit): ArticleService =
        retrofit.create(ArticleService::class.java)

    companion object {
        private const val BASE_URL: String = "https://api.spaceflightnewsapi.net"
    }
}