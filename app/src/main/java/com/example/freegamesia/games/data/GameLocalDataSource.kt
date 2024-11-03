package com.example.freegamesia.games.data

import com.example.freegamesia.games.domain.GameRequest
import com.example.freegamesia.games.domain.GameResponse
import kotlinx.coroutines.flow.Flow

interface GameLocalDataSource {

    suspend fun saveAll(gameResponseList: List<GameResponse>)

    suspend fun update(id: Long, gameRequest: GameRequest): GameResponse?

    fun getAll(): Flow<List<GameResponse>>

    suspend fun getById(id: Long): GameResponse?

    fun searchByQuery(query: String): Flow<List<GameResponse>>

    fun searchByCategory(category: String): Flow<List<GameResponse>>

    fun searchWithCategoryByQuery(category: String, query: String): Flow<List<GameResponse>>

    suspend fun delete(id: Long): GameResponse?
}