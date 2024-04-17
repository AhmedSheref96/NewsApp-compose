package com.el3asas.newsapp.ui.screens.homeScreen.favoities

import androidx.paging.PagingData
import com.el3asas.domain.models.ArticlesItem
import kotlinx.coroutines.flow.Flow

sealed class FavoritesScreenStates {
    data object Loading : FavoritesScreenStates()
    data class Error(val throwable: Throwable) : FavoritesScreenStates()
    data class GetData(val data: Flow<PagingData<ArticlesItem>>) : FavoritesScreenStates()
}