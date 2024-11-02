package com.example.freegamesia.games.data

import com.example.freegamesia.games.domain.Game

interface GameRemoteDataSource {

    suspend fun getAll(): List<Game>

    suspend fun getById(id: Long): Game?
}