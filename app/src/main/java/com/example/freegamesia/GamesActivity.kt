package com.example.freegamesia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.freegamesia.games.presentation.GamesNavigationScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                GamesNavigationScreen()
            }
        }
    }
}