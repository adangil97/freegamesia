package com.example.freegamesia.games.usecases

import com.example.freegamesia.games.data.GameRepository

class DeleteGame(private val gameRepository: GameRepository) {

    suspend operator fun invoke(id: Long) = gameRepository.delete(id)
}