package com.example.kotlin_wordle.domain

interface GetWordUseCase {
    suspend operator fun invoke(number: Int): Result<String?>
}

