package com.example.freegamesia.games.usecases

import com.example.freegamesia.games.data.GameRepository
import com.example.freegamesia.games.domain.GameRequest

class UpdateGame(private val gameRepository: GameRepository) {

    suspend operator fun invoke(
        id: Long,
        gameRequest: GameRequest
    ) = gameRepository.update(id, gameRequest)
}