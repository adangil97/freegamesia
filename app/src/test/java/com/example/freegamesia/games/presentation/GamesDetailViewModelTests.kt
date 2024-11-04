package com.example.freegamesia.games.presentation

import app.cash.turbine.test
import com.example.freegamesia.games.domain.GameResponse
import com.example.freegamesia.games.presentation.detail.GameDetailEffects
import com.example.freegamesia.games.presentation.detail.GameDetailViewModel
import com.example.freegamesia.games.usecases.DeleteGame
import com.example.freegamesia.games.usecases.GetGameById
import com.example.freegamesia.games.usecases.UpdateGame
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GamesDetailViewModelTests {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: GameDetailViewModel
    private val getGameById: GetGameById = mockk()
    private val updateGame: UpdateGame = mockk()
    private val deleteGame: DeleteGame = mockk()

    private val gameUiModel = GameUiModel(
        id = 1,
        title = "Game 1",
        description = "Description 1",
        platform = "PC",
        category = "Category",
        image = "",
    )
    private val gameResponse = GameResponse(
        id = 1,
        title = "Game 1",
        description = "Description 1",
        platform = "PC",
        category = "Category",
        image = "",
        developer = "",
        url = "",
        releaseDate = "",
        requireUpdatedAt = 0
    )

    @Before
    fun setUp() {
        viewModel = GameDetailViewModel(getGameById, updateGame, deleteGame)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `getById should set isLoading, then load game and update state`() =
        runTest(testDispatcher) {
            val gameId = 1L
            coEvery { getGameById(gameId) } returns gameResponse

            viewModel.getById(gameId)

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(false, initialState.isLoading)

                val loadingState = awaitItem()
                assertEquals(true, loadingState.isLoading)

                val finalState = awaitItem()
                assertEquals(false, finalState.isLoading)
                assertEquals(gameUiModel, finalState.gameUiModel)
            }

            coVerify { getGameById(gameId) }
        }

    @Test
    fun `update should set isLoading, call updateGame, emit UpdateSuccess effect, and set isLoading to false`() =
        runTest(testDispatcher) {
            coEvery { updateGame(gameUiModel.id, any()) } returns gameResponse

            viewModel.update(gameUiModel)

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(false, initialState.isLoading)

                val loadingState = awaitItem()
                assertEquals(true, loadingState.isLoading)

                val finalState = awaitItem()
                assertEquals(false, finalState.isLoading)
            }

            viewModel.effects.test {
                val updateSuccessEffect = awaitItem()
                assertEquals(GameDetailEffects.UpdateSuccess, updateSuccessEffect)
            }

            coVerify { updateGame(gameUiModel.id, any()) }
        }

    @Test
    fun `delete should set isLoading, call deleteGame, emit DeleteSuccess effect, and set isLoading to false`() =
        runTest(testDispatcher) {
            val gameId = 1L
            coEvery { deleteGame(gameId) } returns gameResponse

            viewModel.delete(gameId)

            viewModel.state.test {
                val initialState = awaitItem()
                assertEquals(false, initialState.isLoading)

                val loadingState = awaitItem()
                assertEquals(true, loadingState.isLoading)

                val finalState = awaitItem()
                assertEquals(false, finalState.isLoading)
            }

            viewModel.effects.test {
                val deleteSuccessEffect = awaitItem()
                assertEquals(GameDetailEffects.DeleteSuccess, deleteSuccessEffect)
            }

            coVerify { deleteGame(gameId) }
        }
}
