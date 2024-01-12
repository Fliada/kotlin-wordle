package com.example.kotlin_wordle.data.repository

import com.example.kotlin_wordle.data.API.GameService
import retrofit2.HttpException
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val service: GameService,
) : GameRepository {

    override suspend fun getWord(number: Int): Result<String?> {
        kotlin.runCatching {
            service.getWord(number)
        }.fold(
            onSuccess = {
                return if (it.isSuccessful)
                    Result.success(it.body())
                else Result.failure(HttpException(it))
            },
            onFailure = {
                return Result.failure(it)
            }
        )
    }
}