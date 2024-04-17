package com.el3asas.newsapp.ui.screens.homeScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Tab(val title: String, val icon: ImageVector) {
    data object WallScreen : Tab("Wall", Icons.Rounded.Home)
    data object Favorites : Tab("Favorites", Icons.Rounded.Favorite)

}