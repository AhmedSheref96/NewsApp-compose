package com.el3asas.newsapp.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class Margin constructor(val margin: Dp) {
    data object Medium : Margin(margin = 8.dp)
    data object Large : Margin(margin = 16.dp)
    data object Small : Margin(margin = 4.dp)
}