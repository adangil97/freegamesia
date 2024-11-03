package com.example.freegamesia.games.presentation.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.freegamesia.games.presentation.GameUiModel

@Composable
@Preview(showSystemUi = true)
fun GamesDetailPreview() {
    GamesDetailContent(
        gamesDetailUiState = GameDetailUiState(
            gameUiModel = GameUiModel(
                id = 1,
                title = "Roblox",
                description = "A free to play sandbox MMO with lots of creation options",
                category = "MMO",
                platform = "PC (Windows)"
            )
        ),
        modifier = Modifier.fillMaxSize(),
        onUpdateState = {},
        onDelete = {}
    ) {}
}