package com.example.freegamesia.games.framework.local

import com.example.freegamesia.games.domain.Game

fun GameEntity.toGame() = Game(
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

fun Game.toGameEntity() = GameEntity(
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