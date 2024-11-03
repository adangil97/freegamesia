package com.example.freegamesia.games.presentation.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.freegamesia.R
import com.example.freegamesia.core.ds.SearchBar

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
            Column {
                TopAppBar(title = { Text(category) })
                Row(modifier = Modifier.padding(12.dp)) {
                    SearchBar(
                        hint = stringResource(id = R.string.search),
                        currentText = screenState.query
                    ) { query ->
                        gamesCategoryViewModel.sendAction(GamesCategoryUiActions.Search(query))
                    }
                }
            }
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