package com.example.kotlin_wordle.domain

interface GetAllWordsUseCase {
    suspend operator fun invoke(): Result<List<String?>?>
}