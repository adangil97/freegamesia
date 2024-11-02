package com.example.freegamesia.games.data

import com.example.freegamesia.core.networkBoundResource
import com.example.freegamesia.games.domain.Game

class GameRepository(
    private val gameLocalDataSource: GameLocalDataSource,
    private val gameRemoteDataSource: GameRemoteDataSource
) {

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

    suspend fun getById(id: Long): Game? {
        return gameLocalDataSource.getById(id) ?: gameRemoteDataSource.getById(id)
    }

    fun searchByQuery(query: String) = gameLocalDataSource.searchByQuery(query)

    fun searchByCategory(category: String) = gameLocalDataSource.searchByCategory(category)
}