package com.example.freegamesia.games.presentation.detail

import com.example.freegamesia.games.presentation.GameUiModel

data class GameDetailUiState(
    val isLoading: Boolean = false,
    val gameUiModel: GameUiModel = GameUiModel()
)