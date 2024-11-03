package com.example.freegamesia.games.framework.local

import com.example.freegamesia.games.domain.GameResponse

fun GameEntity.toGame() = GameResponse(
    id = id,
    title = title,
    description = description,
    category = category,
    platform = platform,
    image = image,
    developer = developer,
    url = url,
    releaseDate = releaseDate,
    requireUpdatedAt = requireUpdatedAt
)

fun GameResponse.toGameEntity() = GameEntity(
    id = id,
    title = title,
    description = description,
    category = category,
    platform = platform,
    image = image,
    developer = developer,
    url = url,
    releaseDate = releaseDate,
    requireUpdatedAt = requireUpdatedAt
)