package com.example.kotlin_wordle.di

import dagger.Module

@Module(
    includes = [
        ViewModelModule::class,
    ]
)
class AppModule