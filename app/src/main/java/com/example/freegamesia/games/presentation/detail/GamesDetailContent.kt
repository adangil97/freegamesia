package com.example.freegamesia.games.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.freegamesia.R
import com.example.freegamesia.core.ds.CircularProgressBar
import com.example.freegamesia.core.ds.GamesHeaderImage
import com.example.freegamesia.games.presentation.GameUiModel

@Composable
fun GamesDetailContent(
    gamesDetailUiState: GameDetailUiState,
    modifier: Modifier = Modifier,
    onUpdateState: (GameUiModel) -> Unit,
    onDelete: () -> Unit,
    onUpdate: (GameUiModel) -> Unit
) {
    Box(modifier = modifier) {
        val game = gamesDetailUiState.gameUiModel
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            GamesHeaderImage(
                imageUrl = game.image,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.padding(24.dp))
            OutlinedTextField(
                value = game.title,
                modifier = Modifier.fillMaxWidth(.7f),
                singleLine = true,
                onValueChange = {
                    onUpdateState(game.copy(title = it))
                }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedTextField(
                value = game.platform,
                modifier = Modifier.fillMaxWidth(.7f),
                singleLine = true,
                onValueChange = {
                    onUpdateState(game.copy(platform = it))
                }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedTextField(
                value = game.description,
                modifier = Modifier.fillMaxWidth(.7f),
                onValueChange = {
                    onUpdateState(game.copy(description = it))
                }
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Button(
                onClick = { onUpdate(game) },
                modifier = Modifier.fillMaxWidth(.5f)
            ) {
                Text(text = stringResource(id = R.string.update))
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Button(
                onClick = onDelete,
                colors = ButtonDefaults
                    .buttonColors()
                    .copy(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth(.5f)
            ) {
                Text(text = stringResource(id = R.string.delete))
            }
            Spacer(modifier = Modifier.padding(4.dp))
        }
        if (gamesDetailUiState.isLoading) {
            CircularProgressBar()
        }
    }
}