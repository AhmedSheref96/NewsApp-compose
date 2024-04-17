package com.el3asas.newsapp.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.el3asas.domain.models.ArticlesItem
import com.el3asas.newsapp.ui.theme.Margin

@Preview(showBackground = true)
@Composable
fun FavorableNewsListView(
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
    onFavClicked: ((ArticlesItem) -> Unit)? = null,
    onUnFavClicked: ((ArticlesItem) -> Unit)? = null,
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = Margin.Medium.margin, Margin.Small.margin)
            .fillMaxSize()
    ) {
        items(itemsCount) {
            val item = getItem(it)
            FavorableNewsItem(
                item = item,
                onFavClicked = onFavClicked,
                onUnFavClicked = onUnFavClicked
            )
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
    }
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = Margin.Medium.margin)
            .fillMaxSize()
    ) {
        items(itemsCount) {
            val item = getItem(it)
            NewsItem(item = item)
        }
    }
}