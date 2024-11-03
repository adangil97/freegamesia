package com.example.freegamesia.games.data

import com.example.freegamesia.core.networkBoundResource
import com.example.freegamesia.games.domain.GameRequest
import com.example.freegamesia.games.domain.GameResponse

class GameRepository(
    private val gameLocalDataSource: GameLocalDataSource,
    private val gameRemoteDataSource: GameRemoteDataSource
) {

    suspend fun update(
        id: Long,
        gameRequest: GameRequest
    ) = gameLocalDataSource.update(id, gameRequest)

    fun getAll(forceRefresh: Boolean) = networkBoundResource(
        query = {
            gameLocalDataSource.getAll()
        },
        fetch = {
            gameRemoteDataSource.getAll()
        },
        saveFetchResult = {
            gameLocalDataSource.saveAll(it)
        },
        shouldFetch = {
            it.isEmpty() ||
                    it.any { business ->
                        // Force update if at least one item exceeds cache time.
                        System.currentTimeMillis() > business.requireUpdatedAt
                    } ||
                    forceRefresh
        }
    )

    suspend fun getById(id: Long): GameResponse? {
        return gameLocalDataSource.getById(id) ?: gameRemoteDataSource.getById(id)
    }

    fun searchByQuery(query: String) = gameLocalDataSource.searchByQuery(query)

    fun searchByCategory(category: String) = gameLocalDataSource.searchByCategory(category)

    suspend fun delete(id: Long) = gameLocalDataSource.delete(id)
}