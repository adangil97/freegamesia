package com.example.freegamesia.games.data

import com.example.freegamesia.core.networkBoundResource

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

    fun searchByQuery(query: String) = gameLocalDataSource.searchByQuery(query)

    fun searchByCategory(category: String) = gameLocalDataSource.searchByCategory(category)
}