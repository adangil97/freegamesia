package com.example.freegamesia.games.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.freegamesia.games.presentation.detail.GameDetailScreen
import com.example.freegamesia.games.presentation.list.GamesListScreen

@Composable
fun GamesNavigationScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "games") {
        composable("games") {
            GamesListScreen {
                navController.navigate("games/$it")
            }
        }
        composable(
            "games/{gameId}",
            arguments = listOf(navArgument("gameId") { type = NavType.LongType })
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getLong("gameId") ?: 0
            GameDetailScreen(gameId = gameId)
        }
    }
}