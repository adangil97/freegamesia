package com.example.freegamesia.games.presentation

data class GameUiModel(
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val platform: String = "",
    val image: String = ""
)
