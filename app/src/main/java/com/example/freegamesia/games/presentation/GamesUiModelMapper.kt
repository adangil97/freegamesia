package com.example.freegamesia.games.presentation

import com.example.freegamesia.games.domain.Game

fun Game.toGameUiModel() = GameUiModel(
    id = id,
    title = title,
    description = description,
    category = category,
    platform = platform,
    image = image
)