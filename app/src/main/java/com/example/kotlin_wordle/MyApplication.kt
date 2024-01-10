package com.example.kotlin_wordle

import android.app.Application
import com.example.kotlin_wordle.di.AppComponent
import com.example.kotlin_wordle.di.DaggerAppComponent

class MyApplication : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .create()
    }

}