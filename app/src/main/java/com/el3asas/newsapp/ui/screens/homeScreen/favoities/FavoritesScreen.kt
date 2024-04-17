package com.el3asas.newsapp.ui.screens.homeScreen.favoities

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.el3asas.domain.models.ArticlesItem
import com.el3asas.newsapp.ui.composables.FavorableNewsListView
import com.el3asas.newsapp.viewModels.FavoritesViewModel

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel = hiltViewModel()) {
    val items = viewModel.favorites.collectAsLazyPagingItems()
    val context = LocalContext.current
//    val searchQuery by viewModel.searchQuery.collectAsState()
    Column {
//        SearchView(searchQuery, viewModel::onSearchQueryChange)
        FavoritesScreenContent(
            items.itemCount,
            getItem = { items[it]!! },
            onUnFavClicked = viewModel::onUnFavClicked,
            onItemClick = {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setData(it.url?.toUri())
                }
                context.startActivity(intent)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritesScreenContent(
    itemsCount: Int = 10,
    getItem: (Int) -> ArticlesItem = {
        ArticlesItem(
            title = "title test",
            description = "description test",
            url = "url test",
            author = "test author",
            publishedAt = "published at test"
        )
    },
    onUnFavClicked: ((ArticlesItem) -> Unit)? = null,
    onItemClick: (ArticlesItem) -> Unit = {}
) {
    FavorableNewsListView(
        itemsCount = itemsCount,
        getItem = getItem,
        onUnFavClicked = onUnFavClicked,
        onItemClicked = onItemClick
    )
}