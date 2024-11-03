package com.example.freegamesia.games.presentation.category

import androidx.lifecycle.viewModelScope
import com.example.freegamesia.core.NoActions
import com.example.freegamesia.core.NoEffects
import com.example.freegamesia.core.StateEffectsViewModel
import com.example.freegamesia.games.presentation.toGameUiModel
import com.example.freegamesia.games.usecases.SearchGamesByCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesCategoryViewModel @Inject constructor(
    private val searchGamesByCategory: SearchGamesByCategory
) : StateEffectsViewModel<GamesCategoryUiState, NoEffects, NoActions>(GamesCategoryUiState()) {

    fun initialize(category: String) {
        viewModelScope.launch {
            mutableState.value = currentState().copy(isLoading = true)
            searchGamesByCategory(category).collect {
                mutableState.value = currentState().copy(
                    isLoading = false,
                    games = it.result.map { game ->
                        game.toGameUiModel()
                    }
                )
            }
        }
    }
}