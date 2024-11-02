package com.example.freegamesia.games.framework.local

import com.example.freegamesia.games.data.GameLocalDataSource
import com.example.freegamesia.games.domain.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameDBDataSource @Inject constructor(
    private val gameDao: GameDao
) : GameLocalDataSource {

    override suspend fun saveAll(games: List<Game>) = withContext(Dispatchers.IO) {
        games.forEach { game ->
            gameDao.save(game.toGameEntity())
        }
    }

    override fun getAll(): Flow<List<Game>> {
        return gameDao.getAll().map { gameEntityList ->
            gameEntityList.map { gameEntity ->
                gameEntity.toGame()
            }
        }
    }

    override fun searchByQuery(query: String): Flow<List<Game>> {
        return gameDao.getAllByQuery(query).map { gameEntityList ->
            gameEntityList.map { gameEntity ->
                gameEntity.toGame()
            }
        }
    }

    override fun searchByCategory(category: String): Flow<List<Game>> {
        return gameDao.getAllByCategory(category).map { gameEntityList ->
            gameEntityList.map { gameEntity ->
                gameEntity.toGame()
            }
        }
    }
}