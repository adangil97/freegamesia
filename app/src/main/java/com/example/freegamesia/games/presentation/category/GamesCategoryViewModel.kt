package com.example.freegamesia.games.presentation.category

import androidx.lifecycle.viewModelScope
import com.example.freegamesia.core.NoEffects
import com.example.freegamesia.core.StateEffectsViewModel
import com.example.freegamesia.games.presentation.toGameUiModel
import com.example.freegamesia.games.usecases.SearchGamesByCategory
import com.example.freegamesia.games.usecases.SearchGamesWithCategoryByQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesCategoryViewModel @Inject constructor(
    private val searchGamesByCategory: SearchGamesByCategory,
    private val searchGamesWithCategoryByQuery: SearchGamesWithCategoryByQuery
) : StateEffectsViewModel<GamesCategoryUiState, NoEffects, GamesCategoryUiActions>(
    initialState = GamesCategoryUiState(),
    initialAction = GamesCategoryUiActions.Initial
) {

    fun initialize(category: String) {
        viewModelScope.launch {
            fetchFromActions(
                request = {
                    mutableState.value = currentState().copy(isLoading = true)
                    when (it) {
                        GamesCategoryUiActions.Initial -> {
                            searchGamesByCategory(category)
                        }

                        is GamesCategoryUiActions.Search -> {
                            mutableState.value = currentState().copy(query = it.query)
                            searchGamesWithCategoryByQuery(category, it.query)
                        }
                    }
                }
            ) {
                val games = it.result
                mutableState.value = currentState().copy(
                    isLoading = false,
                    games = games.map { game ->
                        game.toGameUiModel()
                    }
                )
            }
        }
    }
}