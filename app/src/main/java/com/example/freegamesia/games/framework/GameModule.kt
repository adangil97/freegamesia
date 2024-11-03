package com.example.freegamesia.games.framework

import com.example.freegamesia.core.AppDatabase
import com.example.freegamesia.games.data.GameLocalDataSource
import com.example.freegamesia.games.data.GameRemoteDataSource
import com.example.freegamesia.games.data.GameRepository
import com.example.freegamesia.games.framework.local.GameDBDataSource
import com.example.freegamesia.games.framework.local.GameDao
import com.example.freegamesia.games.framework.remote.GameApiClient
import com.example.freegamesia.games.framework.remote.GameApiDataSource
import com.example.freegamesia.games.usecases.DeleteGame
import com.example.freegamesia.games.usecases.GetGameById
import com.example.freegamesia.games.usecases.GetGames
import com.example.freegamesia.games.usecases.SearchGamesByCategory
import com.example.freegamesia.games.usecases.SearchGamesByQuery
import com.example.freegamesia.games.usecases.SearchGamesWithCategoryByQuery
import com.example.freegamesia.games.usecases.UpdateGame
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

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
        fun gameApiClient(
            retrofit: Retrofit
        ): GameApiClient = retrofit.create(GameApiClient::class.java)

        @Provides
        fun gamesDao(gameDatabase: AppDatabase): GameDao = gameDatabase.gameDao()

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

        @Provides
        fun providesUpdateGame(gameRepository: GameRepository) = UpdateGame(gameRepository)

        @Provides
        fun providesDeleteGame(gameRepository: GameRepository) = DeleteGame(gameRepository)

        @Provides
        fun providesSearchGamesWithCategoryByQuery(
            gameRepository: GameRepository
        ) = SearchGamesWithCategoryByQuery(gameRepository)
    }
}