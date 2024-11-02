package com.example.freegamesia.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.freegamesia.games.framework.local.GameDao
import com.example.freegamesia.games.framework.local.GameEntity

@Database(entities = [GameEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}