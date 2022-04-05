package com.project.readarticleapp.di

import com.project.readarticleapp.repository.ArticleInterface
import com.project.readarticleapp.repository.DefaultArticleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {


    @ActivityRetainedScoped
    @Binds
    abstract fun bindArticleRepository(repository: DefaultArticleRepository): ArticleInterface
}