package com.el3asas.newsapp.ui.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun NewsListShimmer() {
    LazyColumn {
        items(6) {
            NewsItemShimmer()
        }
    }
}