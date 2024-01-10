package com.example.kotlin_wordle.di

import dagger.Component

@Component(
    modules = [ AppModule::class ]
)
interface AppComponent {
}