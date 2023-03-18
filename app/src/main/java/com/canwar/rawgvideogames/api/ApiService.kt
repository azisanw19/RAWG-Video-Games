package com.canwar.rawgvideogames.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("games")
    fun getGames(
        @Query("page") page: Int,
        @Query("page_size") count: Int = 20,
        @Query("key") key: String = "10a79a5e331e40d48d9b7e470d0ff6c5"
    ): Call<GameResponse>


}