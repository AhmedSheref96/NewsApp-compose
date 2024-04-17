package com.el3asas.newsapp.ui.screens.homeScreen.wall

import androidx.paging.PagingData
import com.el3asas.domain.models.ArticlesItem
import kotlinx.coroutines.flow.Flow

sealed class WallScreenStates {
    data class GetWallData(val data: Flow<PagingData<ArticlesItem>>) : WallScreenStates()
    data class GetSearchResult(val data: Flow<PagingData<ArticlesItem>>) : WallScreenStates()
    data object Loading : WallScreenStates()
    data class Error(val throwable: Throwable) : WallScreenStates()

}