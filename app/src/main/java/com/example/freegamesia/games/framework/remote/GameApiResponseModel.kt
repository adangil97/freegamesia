package com.example.freegamesia.games.framework.remote

import com.google.gson.annotations.SerializedName

data class GameApiResponseModel(
    @SerializedName("short_description")
    val shortDescription: String? = null,
    @SerializedName("thumbnail")
    val thumbnail: String? = null,
    @SerializedName("game_url")
    val gameUrl: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("freetogame_profile_url")
    val freetogameProfileUrl: String? = null,
    @SerializedName("genre")
    val genre: String? = null,
    @SerializedName("publisher")
    val publisher: String? = null,
    @SerializedName("developer")
    val developer: String? = null,
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("platform")
    val platform: String? = ""
)