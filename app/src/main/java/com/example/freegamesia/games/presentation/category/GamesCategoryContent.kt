package com.example.freegamesia.games.presentation.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.freegamesia.core.ds.CircularProgressBar
import com.example.freegamesia.games.presentation.GameUiModel
import com.example.freegamesia.games.presentation.list.GamesItemContent

@Composable
fun GamesCategoryContent(
    gamesCategoryUiState: GamesCategoryUiState,
    modifier: Modifier = Modifier,
    onGameClick: (GameUiModel) -> Unit
) {
    Box(modifier = modifier) {
        if (gamesCategoryUiState.isLoading) {
            CircularProgressBar()
        } else {
            LazyColumn {
                items(gamesCategoryUiState.games, key = { it.id }) {
                    GamesItemContent(
                        game = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 12.dp)
                            .clickable { onGameClick(it) }
                    )
                }
            }
        }
    }
}