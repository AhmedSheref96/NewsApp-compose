package com.el3asas.newsapp.ui.screens.homeScreen.wall

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.el3asas.domain.models.ArticlesItem
import com.el3asas.newsapp.ui.composables.FavorableNewsListView
import com.el3asas.newsapp.ui.composables.SearchView
import com.el3asas.newsapp.viewModels.HomeViewModel

@Composable
fun WallScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val screenStates by viewModel.screenStates.collectAsState()
    val context = LocalContext.current
    val searchQuery by viewModel.searchQuery.collectAsState()

    when (screenStates) {
        is WallScreenStates.GetSearchResult -> {
            val items =
                (screenStates as WallScreenStates.GetSearchResult).data.collectAsLazyPagingItems()
            WallScreenContent(
                searchQuery = searchQuery,
                onSearchQueryChanged = {
                    viewModel.produceIntents(WallScreenIntents.GetDataFromSearch(it))
                },
                onItemClick = {
                    viewModel.produceIntents(WallScreenIntents.OpenArticleWebSite(context, it))
                },
                getItem = {
                    items[it]!!
                },
                itemsCount = items.itemCount,
                onFavClicked = {
                    viewModel.produceIntents(WallScreenIntents.AddToFavorites(it))
                }
            )
        }

        is WallScreenStates.GetWallData -> {
            val items =
                (screenStates as WallScreenStates.GetWallData).data.collectAsLazyPagingItems()
            WallScreenContent(
                searchQuery = searchQuery,
                onSearchQueryChanged = {
                    viewModel.produceIntents(WallScreenIntents.GetDataFromSearch(it))
                },
                onItemClick = {
                    viewModel.produceIntents(WallScreenIntents.OpenArticleWebSite(context, it))
                },
                getItem = {
                    items[it]!!
                },
                itemsCount = items.itemCount,
                onFavClicked = {
                    viewModel.produceIntents(WallScreenIntents.AddToFavorites(it))
                }
            )
        }

        else -> {}
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
    searchQuery: String = "",
    onSearchQueryChanged: (String) -> Unit = {},
    onFavClicked: (ArticlesItem) -> Unit = {},
    onItemClick: (ArticlesItem) -> Unit = {}
) {
    Column {
        SearchView(searchQuery, onSearchQueryChanged)
        FavorableNewsListView(
            itemsCount,
            getItem,
            onFavClicked = onFavClicked,
            onItemClicked = onItemClick
        )
    }

}