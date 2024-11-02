package com.example.freegamesia.games.framework.remote

import com.example.freegamesia.games.domain.Game
import java.util.concurrent.TimeUnit

fun GameApiModel.toGame() = Game(
    id = id ?: 0,
    title = title.orEmpty(),
    description = shortDescription.orEmpty(),
    category = genre.orEmpty(),
    platform = platform.orEmpty(),
    image = thumbnail.orEmpty(),
    developer = developer.orEmpty(),
    url = gameUrl.orEmpty(),
    releaseDate = releaseDate.orEmpty(),
    requireUpdatedAt = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1) // One day of cache time
)