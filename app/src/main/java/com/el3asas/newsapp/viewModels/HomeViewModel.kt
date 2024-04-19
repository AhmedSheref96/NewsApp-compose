package com.el3asas.newsapp.viewModels

import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.el3asas.domain.models.ArticlesItem
import com.el3asas.domain.useCases.GetHomeNewsUseCase
import com.el3asas.domain.useCases.SearchNewsUseCase
import com.el3asas.domain.useCases.UpdateOrAddArticleToLocaleDbUseCases
import com.el3asas.newsapp.ui.screens.homeScreen.wall.WallScreenIntents
import com.el3asas.newsapp.ui.screens.homeScreen.wall.WallScreenStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeNewsUseCase: GetHomeNewsUseCase,
    private val searchNewsUseCase: SearchNewsUseCase,
    private val updateOrAddArticleToLocaleDbUseCases: UpdateOrAddArticleToLocaleDbUseCases
) : ViewModel() {

    val screenStates = MutableStateFlow<WallScreenStates>(WallScreenStates.Loading)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
    private var searchJob: Job? = null

    init {
        produceIntents(WallScreenIntents.GetWallData())
    }

    private fun onFavClicked(articlesItem: ArticlesItem) {
        viewModelScope.launch {
            updateOrAddArticleToLocaleDbUseCases(articlesItem.apply {
                isFav = true
            })
        }
    }

    fun produceIntents(intent: WallScreenIntents) {
        when (intent) {
            is WallScreenIntents.GetWallData -> {
                viewModelScope.launch {
                    try {
                        val data = homeNewsUseCase.invoke().cachedIn(viewModelScope)
                        screenStates.emit(WallScreenStates.GetWallData(data))
                    } catch (e: Throwable) {
                        screenStates.emit(WallScreenStates.Error(e))
                    }
                }
            }

            is WallScreenIntents.AddToFavorites -> {
                onFavClicked(intent.item)
            }

            is WallScreenIntents.GetDataFromSearch -> {
                viewModelScope.launch {
                    _searchQuery.emit(intent.search)
                    searchJob?.cancel()
                    search(intent.search)
                }
            }

            is WallScreenIntents.GetDataFromAutoSearch -> {
                viewModelScope.launch {
                    _searchQuery.emit(intent.search)
                    searchJob?.cancel()
                    delay(2000)
                    search(intent.search)
                }
            }

            is WallScreenIntents.OpenArticleWebSite -> {
                val mIntent = Intent(Intent.ACTION_VIEW).apply {
                    setData(intent.item.url?.toUri())
                }
                intent.context.startActivity(mIntent)
            }
        }
    }

    private fun search(search: String) {
        viewModelScope.launch {
            searchJob = launch {
                try {
                    val data = if (search.isEmpty())
                        homeNewsUseCase.invoke().cachedIn(viewModelScope)
                    else
                        searchNewsUseCase.invoke(search).cachedIn(viewModelScope)
                    screenStates.emit(WallScreenStates.GetSearchResult(data))
                } catch (e: Throwable) {
                    screenStates.emit(WallScreenStates.Error(e))
                }
            }
        }

    }

}