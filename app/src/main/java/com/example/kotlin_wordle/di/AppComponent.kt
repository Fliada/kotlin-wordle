package com.example.kotlin_wordle.di

import com.example.kotlin_wordle.presenter.MainFragment
import com.example.kotlin_wordle.presenter.game.GameFragment
import dagger.Component

@Component(
    modules = [ AppModule::class ]
)
interface AppComponent {
    fun inject(fragment: MainFragment)
    fun inject(fragment: GameFragment)
}