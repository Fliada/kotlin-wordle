package com.example.kotlin_wordle.data.repository

import android.util.Log
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
                Log.d("CoolGame", "SUCCESS ${it.body()} ${number}")
                return if (it.isSuccessful)
                    Result.success(it.body()!![0])
                else Result.failure(HttpException(it))
            },
            onFailure = {
                Log.d("CoolGame", "${it.stackTrace[0]}")
                return Result.failure(it)
            }
        )
    }
}