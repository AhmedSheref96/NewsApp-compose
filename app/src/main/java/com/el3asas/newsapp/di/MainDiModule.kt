package com.el3asas.newsapp.di

import com.el3asas.data.repos.NewsRepoImpl
import com.el3asas.domain.repos.NewsRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MainDiModule {
    @Binds
    abstract fun bindNewsRepo(newsRepoImpl: NewsRepoImpl): NewsRepo

}