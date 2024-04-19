package com.el3asas.newsapp.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.el3asas.domain.models.ArticlesItem
import com.el3asas.newsapp.ui.theme.Margin

@Composable
fun FavorableNewsListView(
    items: LazyPagingItems<ArticlesItem>,
    onFavClicked: ((ArticlesItem) -> Unit)? = null,
    onUnFavClicked: ((ArticlesItem) -> Unit)? = null,
    onItemClicked: (ArticlesItem) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = Margin.Medium.margin, Margin.Small.margin)
            .fillMaxSize()
    ) {
        itemsIndexed(items.itemSnapshotList.items) { _, item ->
            FavorableNewsItem(
                item = item,
                onFavClicked = onFavClicked,
                onUnFavClicked = onUnFavClicked,
                onItemClicked
            )
        }
        items.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    items(count = 4) { NewsItemShimmer() } // Initial loading
                }
                loadState.append is LoadState.Loading -> {
                    item { NewsItemShimmer() } // Loading next page
                }
                loadState.refresh is LoadState.Error -> {
                    val e = items.loadState.refresh as LoadState.Error
                    item { Text("Error: ${e.error.localizedMessage}") } // Handle initial load error
                }
                loadState.append is LoadState.Error -> {
                    val e = items.loadState.append as LoadState.Error
                    item { Text("Error loading more: ${e.error.localizedMessage}") } // Handle next page load error
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsListView(
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
    onItemClicked: (ArticlesItem) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = Margin.Medium.margin)
            .fillMaxSize()
    ) {
        items(itemsCount) {
            val item = getItem(it)
            NewsItem(item = item, onItemClicked)
        }
    }
}