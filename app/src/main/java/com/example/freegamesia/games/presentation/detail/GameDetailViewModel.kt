package com.example.freegamesia.games.presentation.detail

import androidx.lifecycle.viewModelScope
import com.example.freegamesia.core.NoActions
import com.example.freegamesia.core.StateActionsViewModel
import com.example.freegamesia.games.presentation.GameUiModel
import com.example.freegamesia.games.presentation.toGameUiModel
import com.example.freegamesia.games.usecases.GetGameById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val getGameById: GetGameById
) : StateActionsViewModel<GameDetailUiState, NoActions>(GameDetailUiState()) {

    fun getById(id: Long) {
        viewModelScope.launch {
            mutableState.value = currentState().copy(isLoading = true)
            val gameUiModel = getGameById(id)?.toGameUiModel()
            mutableState.value = currentState().copy(
                isLoading = false,
                gameUiModel = gameUiModel ?: GameUiModel()
            )
        }
    }
}