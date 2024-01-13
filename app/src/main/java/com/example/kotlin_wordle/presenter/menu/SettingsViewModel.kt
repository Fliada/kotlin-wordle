package com.example.kotlin_wordle.presenter.menu

import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    private var selectedTheme: String = "Light"
    private var selectedDifficulty: String = "5"

    fun setTheme(theme: String) {
        selectedTheme = theme
    }

    fun setDifficulty(difficulty: String) {
        selectedDifficulty = difficulty
    }

    fun getSelectedTheme(): String {
        return selectedTheme
    }

    fun getSelectedDifficulty(): String {
        return selectedDifficulty
    }
}