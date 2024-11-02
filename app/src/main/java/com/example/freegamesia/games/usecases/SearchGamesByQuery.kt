package com.example.freegamesia.games.usecases

import com.example.freegamesia.games.data.GameRepository

class SearchGamesByQuery(private val gameRepository: GameRepository) {

    operator fun invoke(query: String) = gameRepository.searchByQuery(query)
}