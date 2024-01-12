package com.example.kotlin_wordle.domain

import com.example.kotlin_wordle.data.repository.GameRepository
import javax.inject.Inject

class GetWordUseCaseImpl @Inject constructor(
    private val repository: GameRepository,
): GetWordUseCase {

    override suspend fun invoke(number: Int): Result<String?> =
        repository.getWord(number)
}