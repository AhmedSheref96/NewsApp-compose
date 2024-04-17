package com.el3asas.newsapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.el3asas.domain.models.ArticlesItem
import com.el3asas.domain.useCases.GetHomeNewsUseCase
import com.el3asas.domain.useCases.SearchNewsUseCase
import com.el3asas.domain.useCases.UpdateOrAddArticleToLocaleDbUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeNewsUseCase: GetHomeNewsUseCase,
    private val searchNewsUseCase: SearchNewsUseCase,
    private val updateOrAddArticleToLocaleDbUseCases: UpdateOrAddArticleToLocaleDbUseCases
) : ViewModel() {

    var homeNews = homeNewsUseCase.invoke().cachedIn(viewModelScope)
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        startSearch()
    }

    fun onSearchQueryChange(newValue: String) {
        viewModelScope.launch {
            _searchQuery.emit(newValue)
        }
    }

    @OptIn(FlowPreview::class)
    private fun startSearch() {
        viewModelScope.launch {
            searchQuery.debounce(2000).collect {
                homeNews = if (it.isEmpty())
                    homeNewsUseCase.invoke().cachedIn(viewModelScope)
                else
                    searchNewsUseCase.invoke(it).cachedIn(viewModelScope)
            }
        }
    }

    private fun updateOrAddArticles(vararg articlesItem: ArticlesItem) {
        viewModelScope.launch {
            updateOrAddArticleToLocaleDbUseCases(*articlesItem)
        }
    }

    fun onFavClicked(articlesItem: ArticlesItem) {
        viewModelScope.launch {
            updateOrAddArticleToLocaleDbUseCases(articlesItem.apply {
                isFav = isFav.not()
            })
        }
    }

}