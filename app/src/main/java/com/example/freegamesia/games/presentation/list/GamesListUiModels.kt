package com.example.freegamesia.games.presentation.list

import com.example.freegamesia.games.presentation.GameUiModel

data class GamesListUiState(
    val isLoading: Boolean = false,
    val games: List<GameUiModel> = listOf(),
    val query: String = "",
    val category: String = "",
    val isFromError: Boolean = false
)

sealed class GamesListUiActions {

    data class Initial(val forceRefresh: Boolean = false) : GamesListUiActions()

    data class Search(val query: String) : GamesListUiActions()

    data class Filter(val category: String) : GamesListUiActions()
}