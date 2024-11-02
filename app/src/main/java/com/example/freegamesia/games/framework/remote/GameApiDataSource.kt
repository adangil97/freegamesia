package com.example.freegamesia.games.framework.remote

import com.example.freegamesia.games.data.GameRemoteDataSource
import com.example.freegamesia.games.domain.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameApiDataSource @Inject constructor(
    private val gameApiClient: GameApiClient
) : GameRemoteDataSource {

    override suspend fun getAll(): List<Game> = withContext(Dispatchers.IO) {
        try {
            gameApiClient.getAll().body().orEmpty().map { gameApiModel ->
                gameApiModel.toGame()
            }
        } catch (exception: Exception) {
            // log the exception
            listOf()
        }
    }
}