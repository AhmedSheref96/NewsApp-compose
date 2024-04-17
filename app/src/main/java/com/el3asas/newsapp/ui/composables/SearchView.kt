package com.el3asas.newsapp.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.el3asas.newsapp.ui.theme.Margin

@Composable
fun SearchView(value: String = "", onSearchQueryChange: (String) -> Unit = {}) {
    SearchViewContent(value, onSearchQueryChange)
}

@Preview(showBackground = true)
@Composable
private fun SearchViewContent(value: String = "", onSearchQueryChange: (String) -> Unit = {}) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Margin.Large.margin)
            .padding(top = Margin.Medium.margin, bottom = Margin.Small.margin),
        value = value,
        placeholder = { Text(text = "Search News..") },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        shape = MaterialTheme.shapes.medium,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        trailingIcon = {
            if (value.isEmpty().not())
                IconButton(content = {
                    Icon(imageVector = Icons.Rounded.Clear, contentDescription = "clear")
                }, onClick = {
                    onSearchQueryChange("")
                })
        },
        leadingIcon = {
            Icon(imageVector = Icons.Rounded.Search, contentDescription = "")
        },
        onValueChange = onSearchQueryChange
    )
}