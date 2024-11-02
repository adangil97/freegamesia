package com.example.freegamesia.games.usecases

import com.example.freegamesia.games.data.GameRepository

class SearchGamesByCategory(private val gameRepository: GameRepository) {

    operator fun invoke(category: String) = gameRepository.searchByCategory(category)
}