package com.example.freegamesia.games.usecases

import com.example.freegamesia.core.Resource
import com.example.freegamesia.games.data.GameRepository
import com.example.freegamesia.games.domain.GameResponse
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UseCasesTests {

    private val testDispatcher = StandardTestDispatcher()
    private val gameRepository: GameRepository = mockk()
    private lateinit var deleteGame: DeleteGame
    private lateinit var getGameById: GetGameById
    private lateinit var getGames: GetGames
    private lateinit var searchGamesByCategory: SearchGamesByCategory
    private lateinit var searchGamesByQuery: SearchGamesByQuery
    private lateinit var searchGamesWithCategoryByQuery: SearchGamesWithCategoryByQuery

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
            category = "Prueba 2",
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
        deleteGame = DeleteGame(gameRepository)
        getGameById = GetGameById(gameRepository)
        getGames = GetGames(gameRepository)
        searchGamesByCategory = SearchGamesByCategory(gameRepository)
        searchGamesByQuery = SearchGamesByQuery(gameRepository)
        searchGamesWithCategoryByQuery = SearchGamesWithCategoryByQuery(gameRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `deleteGame by id should call gameRepository delete with correct ID and return the result`() =
        runTest(testDispatcher) {
            val testGameResponse = gameTestList.first()
            val gameId = 1L

            coEvery { gameRepository.delete(gameId) } returns testGameResponse

            val result = deleteGame(gameId)
            assertEquals(testGameResponse, result)

            coVerify { gameRepository.delete(gameId) }
        }

    @Test
    fun `getGameById should call gameRepository getById with correct ID and return the result`() =
        runTest(testDispatcher) {
            val testGameResponse = gameTestList.first()
            val gameId = 1L

            coEvery { gameRepository.getById(gameId) } returns testGameResponse

            val result = getGameById.invoke(gameId)
            assertEquals(testGameResponse, result)

            coVerify { gameRepository.getById(gameId) }
        }

    @Test
    fun `getGames should call gameRepository getAll with correct forceRefresh and return the result`() =
        runTest(testDispatcher) {
            coEvery { gameRepository.getAll(forceRefresh = true) } returns flowOf(
                Resource.Success(
                    gameTestList
                )
            )

            val resource = getGames.invoke(forceRefresh = true).last() as Resource.Success
            assertEquals(gameTestList, resource.result)

            coVerify { gameRepository.getAll(forceRefresh = true) }
        }

    @Test
    fun `searchGamesByCategory should call gameRepository searchByCategory with correct category and return Resource Success`() =
        runTest(testDispatcher) {
            val category = "Acción"
            coEvery { gameRepository.searchByCategory(category) } returns flowOf(gameTestList)

            val resource = searchGamesByCategory.invoke(category).last()
            assertEquals(Resource.Success(gameTestList), resource)

            coVerify { gameRepository.searchByCategory(category) }
        }

    @Test
    fun `searchGamesByQuery should call gameRepository searchByQuery with correct query and return Resource Success`() = runTest(testDispatcher) {
        val query = "Hola"
        coEvery { gameRepository.searchByQuery(query) } returns flowOf(gameTestList)

        val resource = searchGamesByQuery.invoke(query).last()
        assertEquals(Resource.Success(gameTestList), resource)

        coVerify { gameRepository.searchByQuery(query) }
    }

    @Test
    fun `searchGamesWithCategoryByQuery should call gameRepository searchWithCategoryByQuery with correct category and query and return Resource Success`() = runTest(testDispatcher) {
        val category = "Acción"
        val query = "Hola"
        coEvery { gameRepository.searchWithCategoryByQuery(category, query) } returns flowOf(gameTestList)

        val resource = searchGamesWithCategoryByQuery.invoke(category, query).last()
        assertEquals(Resource.Success(gameTestList), resource)

        coVerify { gameRepository.searchWithCategoryByQuery(category, query) }
    }
}