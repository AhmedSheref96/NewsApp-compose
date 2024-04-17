package com.el3asas.newsapp.ui.screens.homeScreen.favoities

import com.el3asas.domain.models.ArticlesItem

sealed class FavoritesScreenIntents {
    data object GetFavoritesItems : FavoritesScreenIntents()

    data class DeleteFavoriteItem(val item: ArticlesItem) : FavoritesScreenIntents()

    data class SearchFavoritesAboutNews(val search: String) : FavoritesScreenIntents()

}