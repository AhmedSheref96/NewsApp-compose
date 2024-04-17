package com.el3asas.domain.repos

import androidx.paging.PagingData
import com.el3asas.domain.models.ArticlesItem
import kotlinx.coroutines.flow.Flow

interface NewsRepo {
    fun getHomeNews(country: String? = null): Flow<PagingData<ArticlesItem>>

    fun searchNews(search: String): Flow<PagingData<ArticlesItem>>

    fun getFavoritesNews(): Flow<PagingData<ArticlesItem>>
    fun getSavedNews(): Flow<PagingData<ArticlesItem>>

    suspend fun updateOrAddNews(vararg articlesItem: ArticlesItem)
}