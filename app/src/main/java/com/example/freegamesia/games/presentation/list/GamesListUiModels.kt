package com.example.freegamesia.games.presentation.list

import com.example.freegamesia.games.presentation.GameUiModel

data class GamesListUiState(
    val isLoading: Boolean = true,
    val games: Map<String, List<GameUiModel>> = mapOf(),
    val query: String = "",
    val isFromError: Boolean = false
)

sealed class GamesListUiActions {

    data object Initial : GamesListUiActions()

    data object Refresh : GamesListUiActions()

    data class Search(val query: String) : GamesListUiActions()
}