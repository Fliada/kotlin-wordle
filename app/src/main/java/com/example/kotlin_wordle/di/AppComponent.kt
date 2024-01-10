package com.example.kotlin_wordle.di

import com.example.kotlin_wordle.presenter.MainFragment
import dagger.Component

@Component(
    modules = [ AppModule::class ]
)
interface AppComponent {
    fun inject(fragment: MainFragment)
}