package com.example.freegamesia.games.framework.local

import com.example.freegamesia.games.data.GameLocalDataSource
import com.example.freegamesia.games.domain.GameRequest
import com.example.freegamesia.games.domain.GameResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameDBDataSource @Inject constructor(
    private val gameDao: GameDao
) : GameLocalDataSource {

    override suspend fun saveAll(gameResponseList: List<GameResponse>) =
        withContext(Dispatchers.IO) {
            gameResponseList.forEach { game ->
                gameDao.save(game.toGameEntity())
            }
        }

    override suspend fun update(id: Long, gameRequest: GameRequest) = withContext(Dispatchers.IO) {
        getById(id)?.let {
            val updatedGame = it.copy(
                title = gameRequest.title,
                platform = gameRequest.platform,
                description = gameRequest.description
            )
            gameDao.save(updatedGame.toGameEntity())
            updatedGame
        }
    }

    override fun getAll(): Flow<List<GameResponse>> {
        return gameDao.getAll().map { gameEntityList ->
            gameEntityList.map { gameEntity ->
                gameEntity.toGame()
            }
        }
    }

    override suspend fun getById(id: Long): GameResponse? = withContext(Dispatchers.IO) {
        gameDao.getById(id)?.toGame()
    }

    override fun searchByQuery(query: String): Flow<List<GameResponse>> {
        return gameDao.findAllByQuery(query).map { gameEntityList ->
            gameEntityList.map { gameEntity ->
                gameEntity.toGame()
            }
        }
    }

    override fun searchByCategory(category: String): Flow<List<GameResponse>> {
        return gameDao.findAllByCategory(category).map { gameEntityList ->
            gameEntityList.map { gameEntity ->
                gameEntity.toGame()
            }
        }
    }

    override fun searchWithCategoryByQuery(
        category: String,
        query: String
    ): Flow<List<GameResponse>> {
        return gameDao.findAllWithCategoryByQuery(
            category = category,
            query = query
        ).map { gameEntityList ->
            gameEntityList.map { gameEntity ->
                gameEntity.toGame()
            }
        }
    }

    override suspend fun delete(id: Long) = withContext(Dispatchers.IO) {
        getById(id)?.let {
            gameDao.delete(it.toGameEntity())
            it
        }
    }
}