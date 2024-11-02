package com.example.freegamesia.games.data

import com.example.freegamesia.games.domain.Game
import kotlinx.coroutines.flow.Flow

interface GameLocalDataSource {

    suspend fun saveAll(games: List<Game>)

    fun getAll(): Flow<List<Game>>

    suspend fun getById(id: Long): Game?

    fun searchByQuery(query: String): Flow<List<Game>>

    fun searchByCategory(category: String): Flow<List<Game>>
}