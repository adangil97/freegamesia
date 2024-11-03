package com.example.freegamesia.games.usecases

import com.example.freegamesia.core.Resource
import com.example.freegamesia.games.data.GameRepository
import kotlinx.coroutines.flow.map

class SearchGamesWithCategoryByQuery(private val gamesRepository: GameRepository) {

    operator fun invoke(
        category: String,
        query: String
    ) = gamesRepository.searchWithCategoryByQuery(category, query).map {
        Resource.Success(it)
    }
}