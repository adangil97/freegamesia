package com.example.freegamesia.games.domain

data class Game(
    val id: Long,
    val title: String,
    val description: String,
    val category: String,
    val platform: String,
    val image: String,
    val developer: String,
    val url: String,
    val releaseDate: String,
    val requireUpdatedAt: Long
)
