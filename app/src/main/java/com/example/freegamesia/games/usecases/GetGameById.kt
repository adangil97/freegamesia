package com.example.freegamesia.games.usecases

import com.example.freegamesia.games.data.GameRepository

class GetGameById(private val gameRepository: GameRepository) {

    suspend operator fun invoke(id: Long) = gameRepository.getById(id)
}