package com.example.kotlin_wordle.di

import com.example.kotlin_wordle.data.repository.GameRepository
import com.example.kotlin_wordle.data.repository.GameRepositoryImpl
import com.example.kotlin_wordle.domain.GetWordUseCase
import com.example.kotlin_wordle.domain.GetWordUseCaseImpl
import dagger.Binds
import dagger.Module


@Module
interface AppBindsModule {
    @Binds
    fun bindSampleRepository(repository: GameRepositoryImpl): GameRepository

    @Binds
    fun bindGetJokesCategoriesUseCase(useCase: GetWordUseCaseImpl): GetWordUseCase
}