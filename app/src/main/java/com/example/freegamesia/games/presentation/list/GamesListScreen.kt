package com.example.freegamesia.games.presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun GamesListScreen(
    gamesListViewModel: GamesListViewModel = hiltViewModel(),
    onNavigate: (Long) -> Unit
) {
    val screenState by gamesListViewModel.state.collectAsState()
    GamesListByStateContent(
        gamesListState = screenState,
        onRetry = {
            gamesListViewModel.sendAction(GamesListUiActions.Initial())
        }
    ) {
        onNavigate(it.id)
    }
    LaunchedEffect(false) {
        gamesListViewModel.initialize()
    }
}