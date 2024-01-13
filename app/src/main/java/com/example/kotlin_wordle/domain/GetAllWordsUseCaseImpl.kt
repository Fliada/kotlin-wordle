package com.example.kotlin_wordle.domain

import com.example.kotlin_wordle.data.repository.GameRepository
import javax.inject.Inject

class GetAllWordsUseCaseImpl @Inject constructor(
    private val repository: GameRepository,
): GetAllWordsUseCase {

    override suspend fun invoke(): Result<List<String?>?> =
        repository.getAllWords()
}