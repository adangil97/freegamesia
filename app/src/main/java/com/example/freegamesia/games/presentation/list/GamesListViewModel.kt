package com.example.freegamesia.games.presentation.list

import androidx.lifecycle.viewModelScope
import com.example.freegamesia.core.NoEffects
import com.example.freegamesia.core.Resource
import com.example.freegamesia.core.StateEffectsViewModel
import com.example.freegamesia.games.domain.GameResponse
import com.example.freegamesia.games.presentation.toGameUiModel
import com.example.freegamesia.games.usecases.GetGames
import com.example.freegamesia.games.usecases.SearchGamesByQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesListViewModel @Inject constructor(
    private val getGames: GetGames,
    private val searchGamesByQuery: SearchGamesByQuery
) : StateEffectsViewModel<GamesListUiState, NoEffects, GamesListUiActions>(
    GamesListUiState(),
    GamesListUiActions.Initial()
) {

    fun initialize() {
        viewModelScope.launch {
            fetchFromActions(
                request = {
                    when (it) {
                        is GamesListUiActions.Initial -> {
                            getGames()
                        }

                        is GamesListUiActions.Refresh -> {
                            mutableState.value = currentState().copy(isLoading = true)
                            getGames(forceRefresh = true)
                        }

                        is GamesListUiActions.Search -> {
                            mutableState.value = currentState().copy(query = it.query)
                            searchGamesByQuery(it.query)
                        }
                    }
                }
            ) { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        val isRefresh = currentAction() is GamesListUiActions.Refresh
                        val result = resource.result.orEmpty()
                        if (result.isEmpty() || isRefresh) {
                            mutableState.value = currentState().copy(isLoading = true)
                        } else {
                            makeResult(result)
                        }
                    }

                    is Resource.Success -> {
                        makeResult(resource.result)
                    }

                    is Resource.Error -> {
                        val result = resource.result.orEmpty()
                        makeResult(
                            gameResponseRespons = result,
                            isFromError = true
                        )
                    }
                }
            }
        }
    }

    private fun makeResult(
        gameResponseRespons: List<GameResponse>,
        isFromError: Boolean = false
    ) {
        mutableState.value = currentState().copy(
            games = gameResponseRespons.map {
                it.toGameUiModel()
            }.groupBy {
                it.category
            },
            isLoading = false,
            isFromError = isFromError
        )
    }
}