package com.el3asas.newsapp.ui.screens.homeScreen.wall

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.el3asas.domain.models.ArticlesItem
import com.el3asas.newsapp.ui.composables.FavorableNewsListView
import com.el3asas.newsapp.ui.composables.NewsItemShimmer
import com.el3asas.newsapp.ui.composables.NewsListShimmer
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
                items = items,
                searchQuery = searchQuery,
                onSearchQueryChanged = {
                    viewModel.produceIntents(WallScreenIntents.GetDataFromAutoSearch(it))
                },
                onSearchClicked = {
                    viewModel.produceIntents(WallScreenIntents.GetDataFromSearch(it))
                },
                onItemClick = {
                    viewModel.produceIntents(WallScreenIntents.OpenArticleWebSite(context, it))
                },
                onFavClicked = {
                    viewModel.produceIntents(WallScreenIntents.AddToFavorites(it))
                }
            )
        }

        is WallScreenStates.GetWallData -> {
            val items =
                (screenStates as WallScreenStates.GetWallData).data.collectAsLazyPagingItems()
            WallScreenContent(
                items = items,
                searchQuery = searchQuery,
                onSearchQueryChanged = {
                    viewModel.produceIntents(WallScreenIntents.GetDataFromSearch(it))
                },
                onSearchClicked = {
                    viewModel.produceIntents(WallScreenIntents.GetDataFromSearch(it))
                },
                onItemClick = {
                    viewModel.produceIntents(WallScreenIntents.OpenArticleWebSite(context, it))
                },
                onFavClicked = {
                    viewModel.produceIntents(WallScreenIntents.AddToFavorites(it))
                }
            )
        }

        is WallScreenStates.Loading -> {
            NewsListShimmer()
        }

        is WallScreenStates.Error -> {}
    }

}

@Composable
private fun WallScreenContent(
    items: LazyPagingItems<ArticlesItem>,
    searchQuery: String = "",
    onSearchQueryChanged: (String) -> Unit = {},
    onFavClicked: (ArticlesItem) -> Unit = {},
    onItemClick: (ArticlesItem) -> Unit = {},
    onSearchClicked: (String) -> Unit = {}
) {
    Column {
        SearchView(searchQuery, onSearchQueryChanged, onSearchClicked)
        FavorableNewsListView(
            items = items,
            onFavClicked = onFavClicked,
            onItemClicked = onItemClick
        )
    }

}