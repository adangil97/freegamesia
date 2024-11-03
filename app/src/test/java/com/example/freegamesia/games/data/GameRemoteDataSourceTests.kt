package com.example.freegamesia.games.data

import com.example.freegamesia.games.domain.GameResponse
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GameRemoteDataSourceTests {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var gameRemoteDataSource: GameRemoteDataSource
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
        gameRemoteDataSource = mockk()
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getAll should return list of games`() = runTest(testDispatcher) {
        coEvery { gameRemoteDataSource.getAll() } returns gameTestList

        val result = gameRemoteDataSource.getAll()
        assertEquals(gameTestList, result)

        coVerify { gameRemoteDataSource.getAll() }
    }

    @Test
    fun `getById should return game when it exists`() = runTest(testDispatcher) {
        val game = gameTestList.first()
        coEvery { gameRemoteDataSource.getById(1) } returns game

        val result = gameRemoteDataSource.getById(1)
        assertEquals(game, result)

        coVerify { gameRemoteDataSource.getById(1) }
    }

    @Test
    fun `getById should return null when game does not exist`() = runTest(testDispatcher) {
        coEvery { gameRemoteDataSource.getById(999) } returns null

        val result = gameRemoteDataSource.getById(999)
        assertNull(result)

        coVerify { gameRemoteDataSource.getById(999) }
    }
}