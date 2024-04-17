package com.el3asas.newsapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.el3asas.domain.models.ArticlesItem
import com.el3asas.domain.useCases.GetFavoritesNewsUseCase
import com.el3asas.domain.useCases.UpdateOrAddArticleToLocaleDbUseCases
import com.el3asas.newsapp.ui.screens.homeScreen.favoities.FavoritesScreenIntents
import com.el3asas.newsapp.ui.screens.homeScreen.favoities.FavoritesScreenStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavorites: GetFavoritesNewsUseCase,
    private val updateOrAddArticleToLocaleDbUseCases: UpdateOrAddArticleToLocaleDbUseCases
) : ViewModel() {

    val screenState = MutableStateFlow<FavoritesScreenStates>(FavoritesScreenStates.Loading)

    init {
        produceIntent(FavoritesScreenIntents.GetFavoritesItems)
    }

    fun produceIntent(intent: FavoritesScreenIntents) {
        when (intent) {
            is FavoritesScreenIntents.GetFavoritesItems -> {
                viewModelScope.launch {
                    try {
                        val data = getFavorites.invoke()
                        screenState.emit(FavoritesScreenStates.GetData(data))
                    } catch (e: Throwable) {
                        screenState.emit(FavoritesScreenStates.Error(e))
                    }
                }
            }

            is FavoritesScreenIntents.DeleteFavoriteItem -> {
                viewModelScope.launch {
                    updateOrAddArticleToLocaleDbUseCases(
                        articlesItem = arrayOf(intent.item.copy(isFav = false))
                    )
                }
            }

            is FavoritesScreenIntents.SearchFavoritesAboutNews -> {}
        }
    }

}