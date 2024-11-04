package com.example.freegamesia.games.presentation

import app.cash.turbine.test
import com.example.freegamesia.core.Resource
import com.example.freegamesia.games.domain.GameResponse
import com.example.freegamesia.games.presentation.list.GamesListUiActions
import com.example.freegamesia.games.presentation.list.GamesListViewModel
import com.example.freegamesia.games.usecases.GetGames
import com.example.freegamesia.games.usecases.SearchGamesByQuery
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GamesListViewModelTests {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: GamesListViewModel
    private val getGames: GetGames = mockk()
    private val searchGamesByQuery: SearchGamesByQuery = mockk()
    private val gameTestList = listOf(
        GameResponse(
            id = 1,
            title = "Hola",
            description = "Prueba",
            category = "Prueba",
            platform = "Android",
            developer = "Developer",
            image = "",
            url = "",
            releaseDate = "",
            requireUpdatedAt = 0
        ),
        GameResponse(
            id = 2,
            title = "Test 2",
            description = "Prueba 2",
            category = "Prueba",
            platform = "iOS",
            developer = "Developer",
            image = "",
            url = "",
            releaseDate = "",
            requireUpdatedAt = 0
        )
    )

    @Before
    fun setUp() {
        viewModel = GamesListViewModel(getGames, searchGamesByQuery)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `initialize with Initial action should load games`() = runTest(testDispatcher) {
        coEvery { getGames() } returns flowOf(Resource.Success(gameTestList))

        viewModel.initialize()

        viewModel.state.test {
            val initialState = awaitItem()
            assertEquals(true, initialState.isLoading)

            val finalState = awaitItem()
            assertEquals(false, finalState.isLoading)
            assertEquals(
                mapOf(
                    "Prueba" to gameTestList.map { game ->
                        game.toGameUiModel()
                    }
                ),
                finalState.games
            )
        }

        coVerify { getGames() }
    }

    @Test
    fun `initialize with Refresh action should refresh games`() = runTest(testDispatcher) {
        coEvery { getGames(forceRefresh = true) } returns flowOf(Resource.Success(gameTestList))

        viewModel.initialize()
        viewModel.sendAction(GamesListUiActions.Refresh())

        viewModel.state.test {
            val initialState = awaitItem()
            assertEquals(true, initialState.isLoading)

            val finalState = awaitItem()
            assertEquals(false, finalState.isLoading)
            assertEquals(
                mapOf(
                    "Prueba" to gameTestList.map { game ->
                        game.toGameUiModel()
                    }
                ),
                finalState.games
            )
        }

        coVerify { getGames(forceRefresh = true) }
    }

    @Test
    fun `initialize with Search action should search games by query`() = runTest(testDispatcher) {
        val query = "Prueba"
        coEvery { searchGamesByQuery(query) } returns flowOf(Resource.Success(gameTestList))

        viewModel.initialize()
        viewModel.sendAction(GamesListUiActions.Search(query = query))

        viewModel.state.test {
            val initialState = awaitItem()
            assertEquals(true, initialState.isLoading)

            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            val finalState = awaitItem()
            assertEquals(false, finalState.isLoading)
            assertEquals(
                mapOf(
                    query to gameTestList.map { game ->
                        game.toGameUiModel()
                    }
                ),
                finalState.games
            )
            assertEquals(query, finalState.query)
        }

        coVerify { searchGamesByQuery(query) }
    }
}
