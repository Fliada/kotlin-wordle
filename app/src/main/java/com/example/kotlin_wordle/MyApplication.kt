package com.example.kotlin_wordle

import android.app.Application
import com.example.kotlin_wordle.di.DaggerAppComponent

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val component = DaggerAppComponent
            .create()
    }

}