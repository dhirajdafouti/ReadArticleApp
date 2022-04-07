package com.project.readarticleapp.di

import android.app.Application
import androidx.room.Room
import com.project.readarticleapp.data.database.ArticleDaoLocalDataSource
import com.project.readarticleapp.data.database.ArticleDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun providesDataBase(appContext: Application): ArticleDataBase {
        return Room.databaseBuilder(appContext, ArticleDataBase::class.java, "ArticleDataBase")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providesDao(dataBase: ArticleDataBase): ArticleDaoLocalDataSource {
        return dataBase.getArticlesDao()
    }
}