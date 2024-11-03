package com.example.freegamesia.games.presentation.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.freegamesia.R
import com.example.freegamesia.core.ds.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesListScreen(
    gamesListViewModel: GamesListViewModel = hiltViewModel(),
    onGoToCategory: (String) -> Unit,
    onGoToDetail: (Long) -> Unit
) {
    val screenState by gamesListViewModel.state.collectAsState()
    Column {
        Row(modifier = Modifier.padding(12.dp)) {
            SearchBar(
                hint = stringResource(id = R.string.search),
                currentText = screenState.query
            ) {
                gamesListViewModel.sendAction(GamesListUiActions.Search(it))
            }
        }
        val pullRefreshState = rememberPullToRefreshState()
        PullToRefreshBox(
            isRefreshing = screenState.isLoading,
            state = pullRefreshState,
            onRefresh = {
                gamesListViewModel.sendAction(GamesListUiActions.Refresh())
            }
        ) {
            GamesListByStateContent(
                gamesListState = screenState,
                onRetry = {
                    gamesListViewModel.sendAction(GamesListUiActions.Initial())
                },
                onCategoryClick = onGoToCategory
            ) {
                onGoToDetail(it.id)
            }
        }
    }
    LaunchedEffect(false) {
        gamesListViewModel.initialize()
    }
}