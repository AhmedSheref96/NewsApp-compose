package com.el3asas.newsapp.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.el3asas.domain.models.ArticlesItem
import com.el3asas.newsapp.R
import com.el3asas.newsapp.ui.theme.Margin
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Preview(showBackground = true)
@Composable
fun FavorableNewsItem(
    item: ArticlesItem = ArticlesItem(
        title = "title test",
        description = "description test",
        url = "url test",
        urlToImage = "test",
        author = "test auther",
        publishedAt = "2024-04-11T17:41:29Z",
        isFav = true
    ),
    onFavClicked: ((ArticlesItem) -> Unit)? = null,
    onUnFavClicked: ((ArticlesItem) -> Unit)? = null,
    onItemClick: (ArticlesItem) -> Unit = {}
) {
    val favSwipeAction = if (onFavClicked != null) {
        SwipeAction(
            onSwipe = { onFavClicked(item) },
            icon = {
                Icon(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    imageVector = Icons.Rounded.Favorite,
                    contentDescription = ""
                )
            },
            weight = 1.0,
            background = Color.Green,
            isUndo = false
        )
    } else null
    val unFavSwipeAction =
        if (onUnFavClicked != null) {
            SwipeAction(
                onSwipe = { onUnFavClicked(item) },
                icon = {
                    Icon(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = ""
                    )
                },
                weight = 1.0,
                background = Color.Red,
                isUndo = false
            )

        } else null

    SwipeableActionsBox(
        endActions = listOfNotNull(unFavSwipeAction),
        startActions = listOfNotNull(favSwipeAction),
        swipeThreshold = 100.dp
    ) {
        NewsItem(item = item, onItemClick = onItemClick)
    }
}

@Composable
fun NewsItem(
    item: ArticlesItem = ArticlesItem(),
    onItemClick: (ArticlesItem) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(Margin.Small.margin)),
        onClick = { onItemClick.invoke(item) }
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (title, description, image, author, publishedAt) = createRefs()

            if (item.urlToImage.isNullOrEmpty().not())
                AsyncImage(
                    model = item.urlToImage,
                    contentDescription = item.description,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.placeholder),
                    modifier = Modifier.constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.ratio("19:9")
                    },
                )

            if (item.title.isNullOrEmpty().not())
                Text(
                    text = item.title ?: "",
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(horizontal = Margin.Medium.margin)
                        .padding(top = Margin.Medium.margin)
                        .constrainAs(title) {
                            top.linkTo(image.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        },
                    style = MaterialTheme.typography.titleMedium
                )

            if (item.publishedAt.isNullOrEmpty().not())
                Text(
                    text = item.publishedAtDate,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(start = Margin.Medium.margin)
                        .constrainAs(publishedAt) {
                            top.linkTo(title.bottom)
                            start.linkTo(parent.start)
                        },
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodySmall
                )

            if (item.author.isNullOrEmpty().not())
                Text(
                    text = item.author ?: "",
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(bottom = Margin.Medium.margin, start = Margin.Medium.margin)
                        .constrainAs(author) {
                            if (item.description
                                    .isNullOrEmpty()
                                    .not()
                            )
                                top.linkTo(description.bottom)
                            else if (item.publishedAt
                                    .isNullOrEmpty()
                                    .not()
                            )
                                top.linkTo(publishedAt.bottom)
                            else if (item.title
                                    .isNullOrEmpty()
                                    .not()
                            ) {
                                top.linkTo(title.bottom)
                            }
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        },
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelMedium
                )


            if (item.description.isNullOrEmpty().not())
                Text(
                    text = item.description ?: "",
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(Margin.Medium.margin)
                        .constrainAs(description) {
                            top.linkTo(publishedAt.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        },
                    maxLines = 6,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodyMedium
                )

        }
    }
}