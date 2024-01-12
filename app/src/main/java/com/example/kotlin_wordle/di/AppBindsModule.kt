package com.example.kotlin_wordle.di

import com.example.kotlin_wordle.data.repository.GameRepositoryImpl
import com.example.kotlin_wordle.domain.GetWordUseCase
import com.example.kotlin_wordle.domain.GetWordUseCaseImpl
import dagger.Binds
import dagger.Module


@Module
interface AppBindsModule {
    @Binds
    fun bindSampleRepository(repository: GameRepositoryImpl): GameRepositoryImpl

    @Binds
    fun bindGetJokesCategoriesUseCase(useCase: GetWordUseCaseImpl): GetWordUseCase
}