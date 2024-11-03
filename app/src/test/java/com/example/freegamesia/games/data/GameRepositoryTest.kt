package com.example.freegamesia.games.data

import com.example.freegamesia.core.Resource
import com.example.freegamesia.games.domain.GameRequest
import com.example.freegamesia.games.domain.GameResponse
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GameRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var gameRepository: GameRepository
    private val gameLocalDataSource: GameLocalDataSource = mockk()
    private val gameRemoteDataSource: GameRemoteDataSource = mockk()

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
        gameRepository = GameRepository(gameLocalDataSource, gameRemoteDataSource)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getAll should fetch from remote when local data is null or empty`() =
        runTest(testDispatcher) {
            coEvery { gameLocalDataSource.getAll() } returns flowOf(emptyList(), gameTestList)
            coEvery { gameRemoteDataSource.getAll() } returns gameTestList
            coEvery { gameLocalDataSource.saveAll(gameTestList) } returns Unit

            val resource = gameRepository.getAll(forceRefresh = false).last() as Resource.Success
            assertEquals(gameTestList, resource.result)

            coVerify { gameLocalDataSource.getAll() }
            coVerify { gameRemoteDataSource.getAll() }
            coVerify { gameLocalDataSource.saveAll(gameTestList) }
        }

    @Test
    fun `getAll should fetch from remote when force refresh is true`() = runTest(testDispatcher) {
        val updatedGameList = gameTestList.map { it.copy(requireUpdatedAt = Long.MAX_VALUE) }
        coEvery { gameLocalDataSource.getAll() } returns flowOf(gameTestList, updatedGameList)
        coEvery { gameRemoteDataSource.getAll() } returns updatedGameList
        coEvery { gameLocalDataSource.saveAll(updatedGameList) } returns Unit

        val resource = gameRepository.getAll(forceRefresh = false).last() as Resource.Success
        assertEquals(updatedGameList, resource.result)
        assertTrue(resource.result.all { it.requireUpdatedAt == Long.MAX_VALUE })

        coVerify { gameLocalDataSource.getAll() }
        coVerify { gameRemoteDataSource.getAll() }
        coVerify { gameLocalDataSource.saveAll(updatedGameList) }
    }

    @Test
    fun `getAll should fetch from remote when any model cache has expired`() =
        runTest(testDispatcher) {
            val updatedGameList = gameTestList.map { it.copy(requireUpdatedAt = Long.MAX_VALUE) }
            coEvery { gameLocalDataSource.getAll() } returns flowOf(gameTestList, updatedGameList)
            coEvery { gameRemoteDataSource.getAll() } returns updatedGameList
            coEvery { gameLocalDataSource.saveAll(updatedGameList) } returns Unit

            val resource = gameRepository.getAll(forceRefresh = false).last() as Resource.Success

            assertEquals(updatedGameList, resource.result)
            assertTrue(resource.result.all { it.requireUpdatedAt == Long.MAX_VALUE })

            coVerify { gameLocalDataSource.getAll() }
            coVerify { gameRemoteDataSource.getAll() }
            coVerify { gameLocalDataSource.saveAll(updatedGameList) }
        }

    @Test
    fun `update should call gameLocalDataSource update`() = runTest(testDispatcher) {
        val gameRequest = GameRequest(
            title = "Nuevo Juego",
            platform = "Android",
            description = "Nueva descripción"
        )
        val id = 1L
        val testGame = gameTestList.first()

        coEvery { gameLocalDataSource.update(id, gameRequest) } returns testGame.copy(
            title = gameRequest.title,
            platform = gameRequest.platform,
            description = gameRequest.description
        )

        gameRepository.update(id, gameRequest)

        coVerify { gameLocalDataSource.update(id, gameRequest) }
    }

    @Test
    fun `getById should return from local if available`() = runTest(testDispatcher) {
        val testGameResponse = gameTestList.first()
        coEvery { gameLocalDataSource.getById(1L) } returns testGameResponse

        val result = gameRepository.getById(1L)

        assertEquals(testGameResponse, result)
        coVerify { gameLocalDataSource.getById(1L) }
        coVerify(exactly = 0) { gameRemoteDataSource.getById(1L) }
    }

    @Test
    fun `getById should return from remote if not available locally`() = runTest(testDispatcher) {
        val testGameResponse = gameTestList.first()
        coEvery { gameLocalDataSource.getById(1L) } returns null
        coEvery { gameRemoteDataSource.getById(1L) } returns testGameResponse

        val result = gameRepository.getById(1L)

        assertEquals(testGameResponse, result)
        coVerify { gameLocalDataSource.getById(1L) }
        coVerify { gameRemoteDataSource.getById(1L) }
    }

    @Test
    fun `delete should call gameLocalDataSource delete`() = runTest(testDispatcher) {
        val testGameResponse = gameTestList.first()
        coEvery { gameLocalDataSource.delete(1L) } returns testGameResponse

        val result = gameRepository.delete(1L)

        assertEquals(testGameResponse, result)
        coVerify { gameLocalDataSource.delete(1L) }
    }
}
