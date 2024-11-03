package com.example.freegamesia.games.data

import com.example.freegamesia.games.domain.GameResponse
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class GameLocalDataSourceTests {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var gameLocalDataSource: GameLocalDataSource
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
        gameLocalDataSource = mockk()
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getAll should return all saved games`() = runTest(testDispatcher) {
        every { gameLocalDataSource.getAll() } returns flowOf(gameTestList)

        val result: List<GameResponse> = gameLocalDataSource.getAll().last()
        assertEquals(gameTestList, result)

        verify { gameLocalDataSource.getAll() }
    }

    @Test
    fun `saveAll should complete without error`() = runTest(testDispatcher) {
        coEvery { gameLocalDataSource.saveAll(any()) } just Runs

        gameLocalDataSource.saveAll(gameTestList.take(1))

        coVerify { gameLocalDataSource.saveAll(any()) }
    }

    @Test
    fun `getById should return game when it exists`() = runTest(testDispatcher) {
        val game = gameTestList.first()
        coEvery { gameLocalDataSource.getById(1) } returns game

        val result = gameLocalDataSource.getById(1)
        assertEquals(game, result)

        coVerify { gameLocalDataSource.getById(1) }
    }

    @Test
    fun `getById should return null when game does not exist`() = runTest(testDispatcher) {
        coEvery { gameLocalDataSource.getById(999) } returns null

        val result = gameLocalDataSource.getById(999)
        assertNull(result)

        coVerify { gameLocalDataSource.getById(999) }
    }

    @Test
    fun `searchByQuery should return games matching query`() = runTest(testDispatcher) {
        every { gameLocalDataSource.searchByQuery("Game") } returns flowOf(gameTestList)

        val result = gameLocalDataSource.searchByQuery("Game").last()
        assertEquals(gameTestList, result)

        verify { gameLocalDataSource.searchByQuery("Game") }
    }

    @Test
    fun `delete should return deleted game when exists`() = runTest(testDispatcher) {
        val deletedGame = gameTestList.first()
        coEvery { gameLocalDataSource.delete(1) } returns deletedGame

        val result = gameLocalDataSource.delete(1)
        assertEquals(deletedGame, result)

        coVerify { gameLocalDataSource.delete(1) }
    }

    @Test
    fun `delete should return null when game does not exist`() = runTest(testDispatcher) {
        coEvery { gameLocalDataSource.delete(999) } returns null

        val result = gameLocalDataSource.delete(999)
        assertNull(result)

        coVerify { gameLocalDataSource.delete(999) }
    }
}