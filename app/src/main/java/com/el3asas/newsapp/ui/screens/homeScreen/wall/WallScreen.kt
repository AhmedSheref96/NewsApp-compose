package com.el3asas.newsapp.ui.screens.homeScreen.wall

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.el3asas.domain.models.ArticlesItem
import com.el3asas.newsapp.ui.composables.FavorableNewsListView
import com.el3asas.newsapp.ui.composables.SearchView
import com.el3asas.newsapp.viewModels.HomeViewModel

@Composable
fun WallScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val items = viewModel.homeNews.collectAsLazyPagingItems()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val context = LocalContext.current
    Column {
        SearchView(searchQuery, viewModel::onSearchQueryChange)
        WallScreenContent(
            items.itemCount,
            getItem = { items[it]!! },
            onFavClicked = viewModel::onFavClicked,
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
private fun WallScreenContent(
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
    onFavClicked: (ArticlesItem) -> Unit = {},
    onItemClick: (ArticlesItem) -> Unit = {}
) {
    FavorableNewsListView(
        itemsCount,
        getItem,
        onFavClicked = onFavClicked,
        onItemClicked = onItemClick
    )
}