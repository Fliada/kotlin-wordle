package com.example.kotlin_wordle.presenter.game

import android.util.Log
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
    // Переменная для хранения ссылки на LettersAdapter
    private var lettersAdapter: LettersAdapter? = null

    // Метод для установки слова
    fun setWord(number: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("CoolGame", "Сюда")
                val result = getWordUseCase(number)
                result.onSuccess {
                    if (it != null) {
                        _wordLiveData.postValue(it)
                        Log.d("CoolGame", "Randomly selected word: $it")
                    }
                }
            }
            catch (e: Exception) {
                Log.e("CoolGame", "Error while getting a word", e)
                // Обработка ошибки, если не удалось получить слово
            }
        }
    }

    // Метод для связывания с LettersAdapter
    fun bindLettersAdapter(adapter: LettersAdapter) {
        lettersAdapter = adapter
    }

    // Метод для проверки совпадения введенных букв с исходным словом
    fun checkWordMatch(row: Int) {
        val word = _wordLiveData.value
        if (word != null) {
            val enteredWord = lettersAdapter?.getEnteredWord()
            if (enteredWord != null) {
                val isWordMatched = enteredWord.joinToString("") == word.toUpperCase()
                Log.d("WordMatch", "Entered Word: ${enteredWord.joinToString("")}, and Actual Word: ${word.toUpperCase()}, Matched: $isWordMatched")

                // Перебор букв и обновление цветов фона
                for (i in enteredWord.indices) {
                    val isCorrectLetter = word.getOrNull(i)?.toString()?.toUpperCase() == enteredWord[i]
                    val isUserLetter = word.toUpperCase().contains(enteredWord[i])
                    lettersAdapter?.updateCellAppearance(row, i, isCorrectLetter, true, isUserLetter)
                }

                // Ваш дополнительный код, если необходимо выполнить какие-либо действия при совпадении слов
            }
        }
    }
}