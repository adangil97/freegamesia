package com.example.freegamesia.games.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.freegamesia.games.presentation.category.GamesCategoryScreen
import com.example.freegamesia.games.presentation.detail.GameDetailScreen
import com.example.freegamesia.games.presentation.list.GamesListScreen

@Composable
fun GamesNavigationScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "games") {
        composable("games") {
            GamesListScreen(
                onGoToCategory = { category ->
                    navController.navigate("games/category/$category")
                }
            ) { gameId ->
                navController.navigate("games/$gameId")
            }
        }
        composable(
            "games/{gameId}",
            arguments = listOf(navArgument("gameId") { type = NavType.LongType })
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getLong("gameId") ?: 0
            GameDetailScreen(gameId = gameId)
        }
        composable(
            "games/category/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category").orEmpty()
            GamesCategoryScreen(category = category) { gameId ->
                navController.navigate("games/$gameId")
            }
        }
    }
}