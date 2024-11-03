package com.example.freegamesia.games.framework.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GameApiClient {

    @GET("games")
    suspend fun getAll(): Response<List<GameApiResponseModel>>

    @GET("game")
    suspend fun getById(@Query("id") id: Long): Response<GameApiResponseModel?>
}