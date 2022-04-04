package com.project.readarticleapp.di

import android.app.Application
import com.project.readarticleapp.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppUtilsModule {

    @Singleton
    @Provides
    fun providesNetworkUtils(appContext: Application): NetworkUtils {
        return NetworkUtils(appContext)
    }
}