package com.example.kotlin_wordle.presenter.game

import android.widget.Button

interface LetterListListener {
    fun onLetterClickListener(entry : Button)
}