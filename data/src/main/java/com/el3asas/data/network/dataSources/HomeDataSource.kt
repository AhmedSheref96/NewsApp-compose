package com.el3asas.data.network.dataSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.el3asas.data.network.apis.NewsApis
import com.el3asas.domain.models.ArticlesItem

class HomeDataSource(private val newsApis: NewsApis) :
    PagingSource<Int, ArticlesItem>() {
    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> = try {
        val page = params.key ?: 1
        val data = newsApis.getHomeNews(page = page).articles?.filterNotNull() ?: emptyList()
        LoadResult.Page(
            nextKey = if (data.isEmpty()) null else page + 1,
            prevKey = if (page == 0) null else page - 1,
            data = data
        )
    } catch (e: Exception) {
        LoadResult.Error(e.cause ?: Throwable("Error while getting data!"))
    }
}