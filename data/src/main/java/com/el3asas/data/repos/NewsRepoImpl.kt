package com.el3asas.data.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.el3asas.data.locale.daos.NewsDao
import com.el3asas.data.locale.entities.ArticleEntity
import com.el3asas.data.locale.entities.mapToEntity
import com.el3asas.data.network.apis.NewsApis
import com.el3asas.data.network.dataSources.HomeDataSource
import com.el3asas.data.network.dataSources.SearchDataSource
import com.el3asas.domain.di.IoDispatcher
import com.el3asas.domain.models.ArticlesItem
import com.el3asas.domain.repos.NewsRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepoImpl @Inject constructor(
    private val newsApis: NewsApis,
    private val newsDao: NewsDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : NewsRepo {

    override fun getHomeNews(country: String?): Flow<PagingData<ArticlesItem>> =
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                HomeDataSource(newsApis)
            }
        ).flow

    override fun searchNews(search: String): Flow<PagingData<ArticlesItem>> =
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                SearchDataSource(newsApis, search)
            }
        ).flow

    override fun getFavoritesNews(): Flow<PagingData<ArticlesItem>> = Pager(
        config = PagingConfig(10),
        pagingSourceFactory = {
            newsDao.getFavorites()
        }
    ).flow.map {
        it.map { entity ->
            entity.mapToItem()
        }
    }

    override fun getSavedNews(): Flow<PagingData<ArticlesItem>> = Pager(
        config = PagingConfig(10),
        pagingSourceFactory = {
            newsDao.getSaved()
        }
    ).flow.map {
        it.map { entity ->
            entity.mapToItem()
        }
    }

    override suspend fun updateOrAddNews(vararg articlesItem: ArticlesItem) = withContext(dispatcher) {
        val articles = articlesItem.map { it.mapToEntity() }.toTypedArray()
        newsDao.addNews(*articles)
    }

}