package com.example.freegamesia.games.presentation.category

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesCategoryScreen(
    category: String,
    gamesCategoryViewModel: GamesCategoryViewModel = hiltViewModel(),
    onGoToDetail: (Long) -> Unit
) {
    val screenState by gamesCategoryViewModel.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(category) })
        }
    ) {
        GamesCategoryContent(
            modifier = Modifier.padding(it),
            gamesCategoryUiState = screenState,
            onGameClick = { game -> onGoToDetail(game.id) }
        )
    }
    LaunchedEffect(false) {
        gamesCategoryViewModel.initialize(category)
    }
}