package com.example.kotlin_wordle.di

import android.content.Context
import com.example.kotlin_wordle.MyApplication

val Context.appComponent: AppComponent
    get() = when(this) {
        is MyApplication -> appComponent
        else -> applicationContext.appComponent
    }