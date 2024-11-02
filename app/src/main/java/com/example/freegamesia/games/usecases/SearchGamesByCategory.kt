package com.example.freegamesia.games.usecases

import com.example.freegamesia.core.Resource
import com.example.freegamesia.games.data.GameRepository
import kotlinx.coroutines.flow.map

class SearchGamesByCategory(private val gameRepository: GameRepository) {

    operator fun invoke(category: String) = gameRepository.searchByCategory(category).map {
        Resource.Success(it)
    }
}