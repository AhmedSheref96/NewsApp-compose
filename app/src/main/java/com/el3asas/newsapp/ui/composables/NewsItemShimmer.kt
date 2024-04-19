package com.el3asas.newsapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.el3asas.newsapp.ui.theme.Margin
import com.valentinilk.shimmer.shimmer

@Preview
@Composable
fun NewsItemShimmer() {
    Card(
        modifier = Modifier
            .shimmer()
            .fillMaxWidth()
            .padding(PaddingValues(Margin.Small.margin)),
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (title, description, image, author, publishedAt) = createRefs()

            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.ratio("19:9")
                },
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(.7f)
                    .height(18.dp)
                    .background(Color.LightGray)
                    .padding(horizontal = Margin.Medium.margin)
                    .padding(top = Margin.Medium.margin)
                    .constrainAs(title) {
                        top.linkTo(image.bottom, margin = 4.dp)
                        start.linkTo(parent.start)
                    }
            )
            Box(
                modifier = Modifier
                    .height(18.dp)
                    .fillMaxWidth(.5f)
                    .background(Color.LightGray)
                    .padding(start = Margin.Medium.margin)
                    .constrainAs(publishedAt) {
                        top.linkTo(title.bottom, margin = 4.dp)
                        start.linkTo(parent.start)
                    }
            )
            Box(
                modifier = Modifier
                    .height(18.dp)
                    .fillMaxWidth(.4f)
                    .background(Color.LightGray)
                    .padding(bottom = Margin.Medium.margin, start = Margin.Medium.margin)
                    .constrainAs(author) {
                        top.linkTo(title.bottom, margin = 4.dp)
                        start.linkTo(parent.start)
                    },
            )

            Box(
                modifier = Modifier
                    .height(18.dp)
                    .fillMaxWidth(.9f)
                    .background(Color.LightGray)
                    .padding(Margin.Medium.margin)
                    .constrainAs(description) {
                        top.linkTo(publishedAt.bottom, margin = 4.dp)
                        start.linkTo(parent.start)
                        width = Dimension.fillToConstraints
                    },
            )

        }
    }
}