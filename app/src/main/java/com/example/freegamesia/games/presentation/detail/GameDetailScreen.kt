package com.example.freegamesia.games.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.freegamesia.R
import kotlinx.coroutines.launch

@Composable
fun GameDetailScreen(
    gameDetailViewModel: GameDetailViewModel = hiltViewModel(),
    gameId: Long,
    onGoToStart: () -> Unit
) {
    Box {
        val screenState by gameDetailViewModel.state.collectAsState()
        val screenEffects by gameDetailViewModel.effects.collectAsState()
        val snackBarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        GamesDetailContent(
            gamesDetailUiState = screenState,
            modifier = Modifier.fillMaxSize(),
            onUpdateState = {
                gameDetailViewModel.updateState(screenState.copy(gameUiModel = it))
            },
            onDelete = {
                gameDetailViewModel.delete(gameId)
            }
        ) {
            gameDetailViewModel.update(it)
        }
        screenEffects?.let {
            when (it) {
                GameDetailEffects.DeleteSuccess -> LaunchedEffect(false) {
                    onGoToStart()
                }

                GameDetailEffects.UpdateSuccess -> {
                    val updateSuccessText = stringResource(id = R.string.update_success)
                    LaunchedEffect(false) {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = updateSuccessText,
                                duration = SnackbarDuration.Indefinite
                            )
                        }
                    }
                }
            }
        }
        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp)
        ) {
            Snackbar(it, containerColor = Color.Green.copy(green = .8f))
        }
        LaunchedEffect(false) {
            gameDetailViewModel.getById(gameId)
        }
    }
}