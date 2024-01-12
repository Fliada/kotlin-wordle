package com.example.kotlin_wordle.presenter.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_wordle.domain.GetWordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameViewModel @Inject constructor (
    private val getWordUseCase: GetWordUseCase,
)
    : ViewModel()  {

    private val _wordLiveData = MutableLiveData<String>()
    val wordLiveData: LiveData<String> get() = _wordLiveData

    // Метод для установки слова
    fun setWord(number: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getWordUseCase(number)
                result.onSuccess {
                    if (it != null)
                        _wordLiveData.postValue(it)
                }
            }
            catch (e: Exception) {
                // Обработка ошибки, если не удалось получить слово
            }
        }
    }
}