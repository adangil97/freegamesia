package com.example.freegamesia.games.framework.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val description: String,
    val gender: String,
    val platform: String,
    val image: String,
    val developer: String,
    val url: String,
    val releaseDate: String,
    val lastUpdate: Long
)
