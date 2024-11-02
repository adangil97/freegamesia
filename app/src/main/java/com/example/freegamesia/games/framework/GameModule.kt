package com.example.freegamesia.games.framework

import com.example.freegamesia.games.data.GameLocalDataSource
import com.example.freegamesia.games.data.GameRemoteDataSource
import com.example.freegamesia.games.data.GameRepository
import com.example.freegamesia.games.framework.local.GameDBDataSource
import com.example.freegamesia.games.framework.remote.GameApiDataSource
import com.example.freegamesia.games.usecases.GetGameById
import com.example.freegamesia.games.usecases.GetGames
import com.example.freegamesia.games.usecases.SearchGamesByCategory
import com.example.freegamesia.games.usecases.SearchGamesByQuery
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GameModule {

    @Binds
    abstract fun gamesRemoteDataSource(
        gameApiDataSource: GameApiDataSource
    ): GameRemoteDataSource

    @Binds
    abstract fun gamesLocalDataSource(
        gameDBDataSource: GameDBDataSource
    ): GameLocalDataSource

    companion object {

        @Provides
        fun provideGameRepository(
            gameLocalDataSource: GameLocalDataSource,
            gameRemoteDataSource: GameRemoteDataSource
        ) = GameRepository(
            gameLocalDataSource = gameLocalDataSource,
            gameRemoteDataSource = gameRemoteDataSource
        )

        @Provides
        fun provideGetGameById(gameRepository: GameRepository) = GetGameById(gameRepository)

        @Provides
        fun providesGetGames(gameRepository: GameRepository) = GetGames(gameRepository)

        @Provides
        fun providesSearchGamesByCategory(
            gameRepository: GameRepository
        ) = SearchGamesByCategory(gameRepository)

        @Provides
        fun providesSearchGamesByQuery(
            gameRepository: GameRepository
        ) = SearchGamesByQuery(gameRepository)
    }
}