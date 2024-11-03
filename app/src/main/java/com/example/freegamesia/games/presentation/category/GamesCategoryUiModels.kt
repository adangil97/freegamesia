package com.example.freegamesia.games.presentation.category

import com.example.freegamesia.games.presentation.GameUiModel

data class GamesCategoryUiState(
    val isLoading: Boolean = false,
    val games: List<GameUiModel> = listOf()
)