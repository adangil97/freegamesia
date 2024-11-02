package com.example.freegamesia.games.data

import com.example.freegamesia.games.domain.Game
import kotlinx.coroutines.flow.Flow

interface GameLocalDataSource {

    fun saveAll(games: List<Game>)

    fun getAll(): Flow<List<Game>>

    suspend fun getById(id: Long): Game?

    fun searchByQuery(query: String): Flow<Game>

    fun searchByCategory(category: String): Flow<Game>
}