package com.example.kotlin_wordle.data.API

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GameService {
    @POST("word/")
    suspend fun getWord(@Query("number") number: Int): Response<String>
}