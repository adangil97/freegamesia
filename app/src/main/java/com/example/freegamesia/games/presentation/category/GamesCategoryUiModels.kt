package com.example.freegamesia.games.presentation.category

import com.example.freegamesia.games.presentation.GameUiModel

data class GamesCategoryUiState(
    val isLoading: Boolean = true,
    val games: List<GameUiModel> = listOf(),
    val query: String = ""
)

sealed class GamesCategoryUiActions {

    data object Initial : GamesCategoryUiActions()

    data class Search(val query: String) : GamesCategoryUiActions()
}