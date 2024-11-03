package com.example.freegamesia.games.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freegamesia.R
import com.example.freegamesia.core.ds.GamesHeaderImage
import com.example.freegamesia.core.ds.GamesTag
import com.example.freegamesia.core.ds.ShimmerComponent
import com.example.freegamesia.core.ds.SimpleContent
import com.example.freegamesia.core.ds.SimpleErrorContent
import com.example.freegamesia.games.presentation.GameUiModel
import kotlinx.coroutines.launch

@Composable
fun GamesListByStateContent(
    gamesListState: GamesListUiState,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
    onCategoryClick: (String) -> Unit,
    onGameClick: (GameUiModel) -> Unit
) {
    Box(modifier = modifier) {
        val snackBarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val errorOccurred = stringResource(id = R.string.network_error_occurred)
        when {
            gamesListState.isLoading -> {
                GameListLoading()
            }

            gamesListState.games.isEmpty() && gamesListState.isFromError -> {
                SimpleErrorContent(
                    msgError = errorOccurred,
                    onRetry = onRetry
                )
            }

            else -> {
                if (gamesListState.games.isNotEmpty()) {
                    GamesListContent(
                        gamesMap = gamesListState.games,
                        onGameClick = onGameClick,
                        onCategoryClick = onCategoryClick
                    )
                } else {
                    SimpleContent(msg = stringResource(id = R.string.no_data))
                }
                if (gamesListState.isFromError) {
                    LaunchedEffect(false) {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = errorOccurred,
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
            Snackbar(it, containerColor = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun GamesListContent(
    gamesMap: Map<String, List<GameUiModel>>,
    modifier: Modifier = Modifier,
    onCategoryClick: (String) -> Unit,
    onGameClick: (GameUiModel) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(gamesMap.keys.toList(), key = { it }) { category ->
            val games = gamesMap[category].orEmpty()
            GamesCategoryListContent(
                category = category,
                games = games,
                modifier = Modifier.clickable {
                    onCategoryClick(category)
                },
                onGameClick = onGameClick
            )
        }
    }
}

@Composable
fun GamesCategoryListContent(
    category: String,
    games: List<GameUiModel>,
    modifier: Modifier = Modifier,
    onGameClick: (GameUiModel) -> Unit
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.padding(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.padding(12.dp))
            Text(
                text = category,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowForwardIos,
                contentDescription = null
            )
            Spacer(modifier = Modifier.padding(12.dp))
        }
        Spacer(modifier = Modifier.padding(12.dp))
        LazyRow {
            items(games, key = { it.id }) {
                GamesItemContent(
                    game = it,
                    modifier = Modifier
                        .width(300.dp)
                        .padding(horizontal = 18.dp)
                        .clickable { onGameClick(it) }
                )
            }
        }
        Spacer(modifier = Modifier.padding(12.dp))
    }
}

@Composable
fun GamesItemContent(
    game: GameUiModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        GamesHeaderImage(
            imageUrl = game.image,
            modifier = Modifier
                .height(150.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
        ) {
            GamesTag(
                tag = game.platform,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Black.copy(alpha = .7f))
                    .padding(4.dp)
                    .align(Alignment.BottomEnd)
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = game.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = game.description,
            modifier = Modifier.padding(horizontal = 8.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun GameListLoading(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(5) {
            GameListCategoryItemLoading()
        }
    }
}

@Composable
fun GameListCategoryItemLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.padding(12.dp))
            ShimmerComponent(
                modifier = Modifier
                    .width(50.dp)
                    .height(24.dp)
                    .weight(1f)
            )
            Spacer(modifier = Modifier.padding(horizontal = 100.dp))
            ShimmerComponent(
                modifier = Modifier
                    .size(32.dp)
            )
            Spacer(modifier = Modifier.padding(12.dp))
        }
        Spacer(modifier = Modifier.padding(12.dp))
        LazyRow {
            items(5) {
                GameListItemLoading(
                    modifier = Modifier
                        .padding(horizontal = 18.dp)
                )
            }
        }
    }
}

@Composable
fun GameListItemLoading(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        ShimmerComponent(
            modifier = Modifier
                .width(300.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.padding(8.dp))
        ShimmerComponent(
            modifier = Modifier
                .width(150.dp)
                .height(24.dp)
                .padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        ShimmerComponent(
            modifier = Modifier
                .width(300.dp)
                .height(24.dp)
                .padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.padding(2.dp))
        ShimmerComponent(
            modifier = Modifier
                .width(300.dp)
                .height(24.dp)
                .padding(horizontal = 8.dp)
        )
    }
}