package com.example.kotlin_wordle.data.repository

interface GameRepository {
    suspend fun getWord(number: Int): Result<String?>
    suspend fun getAllWords(): Result<List<String?>?>
}

