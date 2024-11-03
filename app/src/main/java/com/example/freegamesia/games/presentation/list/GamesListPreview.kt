package com.example.freegamesia.games.presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.freegamesia.games.presentation.GameUiModel

@Composable
@Preview(showSystemUi = true)
fun GamesPreviewList() {
    GamesListContent(
        gamesMap = mapOf(
            "Prueba" to listOf(
                GameUiModel(
                    id = 1,
                    title = "Hola",
                    description = "Prueba",
                    category = "Prueba",
                    platform = "Android"
                ),
                GameUiModel(
                    id = 2,
                    title = "Hola 2",
                    description = "Prueba",
                    category = "Prueba",
                    platform = "Android"
                ),
            ),
            "Prueba 2" to listOf(
                GameUiModel(
                    id = 1,
                    title = "Hola",
                    description = "Prueba 2",
                    category = "Prueba 2",
                    platform = "iOS"
                )
            ),
        ),
        onCategoryClick = {}
    ) {

    }
}