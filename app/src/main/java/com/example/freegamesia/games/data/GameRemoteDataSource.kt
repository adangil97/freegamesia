package com.example.freegamesia.games.data

import com.example.freegamesia.games.domain.GameResponse

interface GameRemoteDataSource {

    suspend fun getAll(): List<GameResponse>

    suspend fun getById(id: Long): GameResponse?
}