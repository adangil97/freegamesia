package com.example.freegamesia.games.presentation

import app.cash.turbine.test
import com.example.freegamesia.core.Resource
import com.example.freegamesia.games.domain.GameResponse
import com.example.freegamesia.games.presentation.category.GamesCategoryUiActions
import com.example.freegamesia.games.presentation.category.GamesCategoryUiState
import com.example.freegamesia.games.presentation.category.GamesCategoryViewModel
import com.example.freegamesia.games.usecases.SearchGamesByCategory
import com.example.freegamesia.games.usecases.SearchGamesWithCategoryByQuery
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
class GamesCategoryViewModelTests {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: GamesCategoryViewModel
    private val searchGamesByCategory: SearchGamesByCategory = mockk()
    private val searchGamesWithCategoryByQuery: SearchGamesWithCategoryByQuery = mockk()

    private val gameUiModelList = listOf(
        GameUiModel(
            id = 1,
            title = "Game 1",
            description = "Description 1",
            category = "Action",
            platform = "Android",
            image = "",
        ),
        GameUiModel(
            id = 2,
            title = "Game 2",
            description = "Description 2",
            category = "Action",
            platform = "Android",
            image = "",
        )
    )

    private val gameResponseList = listOf(
        GameResponse(
            id = 1,
            title = "Game 1",
            description = "Description 1",
            category = "Action",
            platform = "Android",
            developer = "Developer",
            image = "",
            url = "",
            releaseDate = "",
            requireUpdatedAt = 0
        ),
        GameResponse(
            id = 2,
            title = "Game 2",
            description = "Description 2",
            category = "Action",
            platform = "Android",
            developer = "Developer",
            image = "",
            url = "",
            releaseDate = "",
            requireUpdatedAt = 0
        )
    )

    @Before
    fun setUp() {
        viewModel = GamesCategoryViewModel(
            searchGamesByCategory = searchGamesByCategory,
            searchGamesWithCategoryByQuery = searchGamesWithCategoryByQuery
        )
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `initialize with Initial action should call searchGamesByCategory and update state with games`() =
        runTest(testDispatcher) {
            val category = "Action"
            coEvery { searchGamesByCategory(category) } returns flowOf(
                Resource.Success(
                    gameResponseList
                )
            )

            viewModel.initialize(category)

            viewModel.state.test {
                // The initial state of my viewmodel loading is always false.
                val initialState: GamesCategoryUiState = awaitItem()
                assertEquals(false, initialState.isLoading)

                // The next state is loading.
                val loadingState: GamesCategoryUiState = awaitItem()
                assertEquals(true, loadingState.isLoading)

                // And finally.
                val finalState: GamesCategoryUiState = awaitItem()
                assertEquals(false, finalState.isLoading)
                assertEquals(gameUiModelList, finalState.games)
            }

            coVerify { searchGamesByCategory(category) }
        }

    @Test
    fun `initialize with Search action should call searchGamesWithCategoryByQuery and update state with games`() =
        runTest(testDispatcher) {
            val category = "Action"
            val query = "Game"
            coEvery {
                searchGamesWithCategoryByQuery(
                    category,
                    query
                )
            } returns flowOf(Resource.Success(gameResponseList))

            viewModel.initialize(category)
            viewModel.sendAction(GamesCategoryUiActions.Search(query))

            viewModel.state.test {
                // The initial state of my viewmodel loading is always false.
                val initialState: GamesCategoryUiState = awaitItem()
                assertEquals(false, initialState.isLoading)

                // The next state is loading.
                val loadingState: GamesCategoryUiState = awaitItem()
                assertEquals(true, loadingState.isLoading)

                // Use an intermediate state to save query on state.
                val intermediateState: GamesCategoryUiState = awaitItem()
                assertEquals(true, intermediateState.isLoading)

                // And finally.
                val finalState: GamesCategoryUiState = awaitItem()
                assertEquals(query, finalState.query)
                assertEquals(gameUiModelList, finalState.games)
            }

            coVerify { searchGamesWithCategoryByQuery(category, query) }
        }
}
