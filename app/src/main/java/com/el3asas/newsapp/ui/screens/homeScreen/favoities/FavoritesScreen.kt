package com.el3asas.newsapp.ui.screens.homeScreen.favoities

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.el3asas.domain.models.ArticlesItem
import com.el3asas.newsapp.ui.composables.FavorableNewsListView
import com.el3asas.newsapp.viewModels.FavoritesViewModel

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel = hiltViewModel()) {
    val screenStates by viewModel.screenState.collectAsState()

    when (screenStates) {
        is FavoritesScreenStates.GetData -> {
            val items =
                (screenStates as FavoritesScreenStates.GetData).data.collectAsLazyPagingItems()
            val context = LocalContext.current
            Column {
                FavoritesScreenContent(
                    items = items,
                    onUnFavClicked = {
                        viewModel.produceIntent(FavoritesScreenIntents.DeleteFavoriteItem(it))
                    },
                    onItemClick = {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            setData(it.url?.toUri())
                        }
                        context.startActivity(intent)
                    }
                )
            }
        }

        else -> {}
    }
}

@Composable
private fun FavoritesScreenContent(
    items:LazyPagingItems<ArticlesItem>,
    onUnFavClicked: ((ArticlesItem) -> Unit)? = null,
    onItemClick: (ArticlesItem) -> Unit = {}
) {
    FavorableNewsListView(
        items = items,
        onUnFavClicked = onUnFavClicked,
        onItemClicked = onItemClick
    )
}