package com.example.freegamesia.games.usecases

import com.example.freegamesia.core.Resource
import com.example.freegamesia.games.data.GameRepository
import kotlinx.coroutines.flow.map

class SearchGamesByQuery(private val gameRepository: GameRepository) {

    operator fun invoke(query: String) = gameRepository.searchByQuery(query).map {
        Resource.Success(it)
    }
}