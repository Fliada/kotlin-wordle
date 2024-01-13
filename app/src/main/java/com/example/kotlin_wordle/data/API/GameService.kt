package com.example.kotlin_wordle.data.API

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GameService {
    @GET("word")
    suspend fun getWord(@Query("length") number: Int): Response<List<String>>

    @GET("all")
    suspend fun getAllWords(): Response<List<String>>
}