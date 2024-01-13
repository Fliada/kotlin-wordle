package com.example.kotlin_wordle.presenter.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_wordle.domain.GetAllWordsUseCase
import com.example.kotlin_wordle.domain.GetWordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameViewModel @Inject constructor (
    private val getWordUseCase: GetWordUseCase,
    private val getAllWordsUseCase: GetAllWordsUseCase,
)
    : ViewModel()  {

    private val _wordLiveData = MutableLiveData<String>()
    val wordLiveData: LiveData<String> get() = _wordLiveData
    // Переменная для хранения ссылки на LettersAdapter
    private var lettersAdapter: LettersAdapter? = null
    private val _gameFinishedLiveData = MutableLiveData<Unit>()
    val gameFinishedLiveData: LiveData<Unit> get() = _gameFinishedLiveData
    var isGameFinished : Boolean = false

    private val _allWordsData = MutableLiveData<List<String>?>()
    val allWordsData: MutableLiveData<List<String>?> get() = _allWordsData

    fun getAllWords() {
        viewModelScope.launch {
            val allWordsResult = getAllWordsUseCase()
            if (allWordsResult.isSuccess) {
                val allWords = allWordsResult.getOrNull()
                _allWordsData.postValue(allWords as List<String>?)
            }
        }
    }

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

                if (allWordsData.value != null && !(allWordsData.value!!.contains(enteredWord.joinToString("").toLowerCase()))) {
                    Log.d("WordMatch", allWordsData.value!!.size.toString())
                    throw Exception()
                }

                val isWordMatched = enteredWord.joinToString("") == word.toUpperCase()
                Log.d("WordMatch", "Entered Word: ${enteredWord.joinToString("")}, and Actual Word: ${word.toUpperCase()}, Matched: $isWordMatched")

                // Перебор букв и обновление цветов фона
                for (i in enteredWord.indices) {
                    val isCorrectLetter = word.getOrNull(i)?.toString()?.toUpperCase() == enteredWord[i]
                    val isUserLetter = word.toUpperCase().contains(enteredWord[i])
                    lettersAdapter?.updateCellAppearance(row, i, isCorrectLetter, true, isUserLetter)
                }

                // Проверка завершения игры
                if (isWordMatched) {
                    isGameFinished = true
                    _gameFinishedLiveData.postValue(Unit)
                }
            }
        }
    }
}