package com.el3asas.newsapp.ui.screens.homeScreen.wall

import android.content.Context
import com.el3asas.domain.models.ArticlesItem

sealed class WallScreenIntents {
    data class GetWallData(val country: String? = null) : WallScreenIntents()
    data class GetDataFromSearch(val search: String) : WallScreenIntents()
    data class GetDataFromAutoSearch(val search: String) : WallScreenIntents()
    data class AddToFavorites(val item: ArticlesItem) : WallScreenIntents()
    data class OpenArticleWebSite(val context: Context, val item: ArticlesItem) :
        WallScreenIntents()
}