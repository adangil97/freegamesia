package com.example.freegamesia.games.presentation.list

import androidx.lifecycle.viewModelScope
import com.example.freegamesia.core.Resource
import com.example.freegamesia.core.StateEffectViewModel
import com.example.freegamesia.games.presentation.toGameUiModel
import com.example.freegamesia.games.usecases.GetGames
import com.example.freegamesia.games.usecases.SearchGamesByCategory
import com.example.freegamesia.games.usecases.SearchGamesByQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesListViewModel @Inject constructor(
    private val getGames: GetGames,
    private val searchGamesByQuery: SearchGamesByQuery,
    private val searchGamesByCategory: SearchGamesByCategory
) : StateEffectViewModel<GamesListUiState, GamesListUiActions>(
    GamesListUiState(),
    GamesListUiActions.Initial()
) {

    fun initialize() {
        viewModelScope.launch {
            fetchFromActions(
                request = {
                    when (it) {
                        is GamesListUiActions.Initial -> {
                            getGames(it.forceRefresh)
                        }

                        is GamesListUiActions.Filter -> {
                            mutableState.value = currentState().copy(category = it.category)
                            searchGamesByCategory(it.category)
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
                        mutableState.value = currentState().copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        mutableState.value = currentState().copy(
                            isLoading = false,
                            games = resource.result.map { game ->
                                game.toGameUiModel()
                            }
                        )
                    }

                    is Resource.Error -> {
                        val result = resource.result.orEmpty()
                        mutableState.value = currentState().copy(
                            games = result.map { game ->
                                game.toGameUiModel()
                            },
                            isLoading = false,
                            isFromError = true
                        )
                    }
                }
            }
        }
    }
}