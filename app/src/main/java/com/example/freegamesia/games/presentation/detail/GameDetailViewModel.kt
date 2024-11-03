package com.example.freegamesia.games.presentation.detail

import androidx.lifecycle.viewModelScope
import com.example.freegamesia.core.NoActions
import com.example.freegamesia.core.StateEffectsViewModel
import com.example.freegamesia.games.domain.GameRequest
import com.example.freegamesia.games.presentation.GameUiModel
import com.example.freegamesia.games.presentation.toGameUiModel
import com.example.freegamesia.games.usecases.DeleteGame
import com.example.freegamesia.games.usecases.GetGameById
import com.example.freegamesia.games.usecases.UpdateGame
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val getGameById: GetGameById,
    private val updateGame: UpdateGame,
    private val deleteGame: DeleteGame
) : StateEffectsViewModel<GameDetailUiState, GameDetailEffects, NoActions>(GameDetailUiState()) {

    fun getById(id: Long) {
        viewModelScope.launch {
            mutableState.value = currentState().copy(isLoading = true)
            delay(1000) // to emulate loading.
            val gameUiModel = getGameById(id)?.toGameUiModel()
            mutableState.value = currentState().copy(
                isLoading = false,
                gameUiModel = gameUiModel ?: GameUiModel()
            )
        }
    }

    fun update(gameUiModel: GameUiModel) {
        viewModelScope.launch {
            mutableState.value = currentState().copy(isLoading = true)
            delay(1000) // to emulate loading.
            updateGame(
                id = gameUiModel.id,
                gameRequest = GameRequest(
                    title = gameUiModel.title,
                    platform = gameUiModel.platform,
                    description = gameUiModel.description
                )
            )
            launchEffect(GameDetailEffects.UpdateSuccess)
            mutableState.value = currentState().copy(isLoading = false)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            mutableState.value = currentState().copy(isLoading = true)
            delay(1000) // to emulate loading.
            deleteGame(id)
            launchEffect(GameDetailEffects.DeleteSuccess)
            mutableState.value = currentState().copy(isLoading = false)
        }
    }
}