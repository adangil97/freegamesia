package com.example.freegamesia.games.presentation.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesListScreen(
    gamesListViewModel: GamesListViewModel = hiltViewModel(),
    onNavigate: (Long) -> Unit
) {
    val screenState by gamesListViewModel.state.collectAsState()
    PullToRefreshBox(
        isRefreshing = screenState.isLoading,
        onRefresh = {
            gamesListViewModel.sendAction(GamesListUiActions.Refresh)
        }
    ) {
        Column {
            Row(modifier = Modifier.padding(12.dp)) {
                SearchBar(hint = "Buscar...") {
                    gamesListViewModel.sendAction(GamesListUiActions.Search(it))
                }
            }
            GamesListByStateContent(
                gamesListState = screenState,
                onRetry = {
                    gamesListViewModel.sendAction(GamesListUiActions.Initial)
                }
            ) {
                onNavigate(it.id)
            }
        }
    }
    LaunchedEffect(false) {
        gamesListViewModel.initialize()
    }
}