package com.example.kotlin_wordle.presenter.menu

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel (
    private val preferences: SharedPreferences
) : ViewModel() {
    private val _selectedDifficulty = MutableLiveData<String>()
    val selectedDifficulty: LiveData<String>
        get() = _selectedDifficulty

    init {
        // Загрузите сохраненное значение difficulty при создании ViewModel
        _selectedDifficulty.value = preferences.getString(KEY_DIFFICULTY, "5")
    }

    fun setDifficulty(difficulty: String) {
        // Сохраните выбранное значение difficulty
        preferences.edit().putString(KEY_DIFFICULTY, difficulty).apply()

        // Обновите LiveData для уведомления наблюдателей (например, фрагментов)
        _selectedDifficulty.value = difficulty
    }

    fun getDifficulty(): String {
        // Возвращает текущее значение difficulty
        return selectedDifficulty.value ?: "5"
    }

    companion object {
        private const val KEY_DIFFICULTY = "difficulty"
    }
}