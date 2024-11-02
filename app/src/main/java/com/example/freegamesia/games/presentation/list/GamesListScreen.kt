package com.example.freegamesia.games.presentation.list

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun GamesListScreen(
    gamesListViewModel: GamesListViewModel = hiltViewModel(),
    onNavigate: (Long) -> Unit
) {

}