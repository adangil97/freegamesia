package com.example.freegamesia.games.framework.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(gameEntity: GameEntity)

    @Query("SELECT * FROM GameEntity")
    fun getAll(): Flow<List<GameEntity>>

    @Query("SELECT * FROM GameEntity WHERE id = :id")
    suspend fun getById(id: Long): GameEntity?

    @Query("SELECT * FROM GameEntity WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun getAllByQuery(query: String): Flow<List<GameEntity>>

    @Query("SELECT * FROM GameEntity WHERE category = :category")
    fun getAllByCategory(category: String): Flow<List<GameEntity>>

    @Delete
    fun delete(gameEntity: GameEntity)
}