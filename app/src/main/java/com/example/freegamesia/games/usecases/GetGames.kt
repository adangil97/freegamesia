package com.example.freegamesia.games.usecases

import com.example.freegamesia.games.data.GameRepository

class GetGames(private val gameRepository: GameRepository) {

    operator fun invoke(forceRefresh: Boolean = false) = gameRepository.getAll(forceRefresh)
}