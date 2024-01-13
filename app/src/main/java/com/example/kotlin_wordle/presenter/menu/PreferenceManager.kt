package com.example.kotlin_wordle.presenter.menu

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "MyAppPrefs"
        private const val KEY_THEME = "theme"
        private const val KEY_DIFFICULTY = "difficulty"
    }

    fun saveTheme(theme: String) {
        sharedPreferences.edit().putString(KEY_THEME, theme).apply()
    }

    fun saveDifficulty(difficulty: String) {
        sharedPreferences.edit().putString(KEY_DIFFICULTY, difficulty).apply()
    }

    fun getTheme(): String {
        return sharedPreferences.getString(KEY_THEME, "Light") ?: "Light"
    }

    fun getDifficulty(): String {
        return sharedPreferences.getString(KEY_DIFFICULTY, "5") ?: "5"
    }
}