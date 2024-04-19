package com.el3asas.newsapp.ui.screens.destinations

sealed class Destination(val route: String, val title: String) {
    data object WallScreen : Destination("wallScreen", "News Wall")
    data object FavoritesScreen : Destination("favoritesScreen", "Favorites News")

    companion object {
        fun getTitle(route: String): String = when (route) {
            WallScreen.route -> WallScreen.title
            FavoritesScreen.route -> FavoritesScreen.title
            else -> ""
        }
    }

}