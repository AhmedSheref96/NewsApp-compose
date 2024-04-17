package com.el3asas.newsapp.ui.screens.homeScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.el3asas.newsapp.ui.screens.homeScreen.favoities.FavoritesScreen
import com.el3asas.newsapp.ui.screens.homeScreen.wall.WallScreen
import com.el3asas.newsapp.ui.theme.Margin
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }

    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf(
        Tab.WallScreen, Tab.Favorites
    )
    val pagerState = rememberPagerState(initialPage = 0) {
        tabs.size
    }
    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        val (pagerLayout, tabsLayout) = createRefs()
        HorizontalPager(
            modifier = Modifier
                .constrainAs(pagerLayout) {
                    top.linkTo(parent.top)
                    bottom.linkTo(tabsLayout.top)
                    height = Dimension.fillToConstraints
                    width = Dimension.matchParent
                },
            state = pagerState,
            userScrollEnabled = false
        ) { page ->
            when (tabs[page]) {
                is Tab.WallScreen -> WallScreen()
                is Tab.Favorites -> FavoritesScreen()
            }
        }


        TabRow(
            modifier = Modifier
                .constrainAs(tabsLayout) {
                    bottom.linkTo(parent.bottom)
                    height = Dimension.wrapContent
                    width = Dimension.matchParent
                },
            selectedTabIndex = selectedTab
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = index == selectedTab,
                    selectedContentColor = MaterialTheme.colorScheme.onBackground,
                    unselectedContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = .4f),
                    onClick = {
                        selectedTab = index
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }) {
                    Icon(
                        modifier = Modifier.padding(top = Margin.Medium.margin),
                        imageVector = tab.icon,
                        contentDescription = tab.title
                    )
                    Text(
                        modifier = Modifier.padding(bottom = Margin.Medium.margin),
                        text = tab.title
                    )
                }
            }
        }
    }

}