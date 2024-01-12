package com.example.kotlin_wordle.presenter.game

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kotlin_wordle.R
import com.example.kotlin_wordle.databinding.FragmentGameBinding
import com.example.kotlin_wordle.di.ViewModelFactory
import com.example.kotlin_wordle.di.appComponent
import javax.inject.Inject

class GameFragment : Fragment(R.layout.fragment_game), LetterListListener {
    private val binding: FragmentGameBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: GameViewModel by viewModels() { viewModelFactory }

    lateinit var adapter: KeyboardAdapter
    lateinit var lettersAdapter: LettersAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val keysRow1 = listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P")
        val keysRow2 = listOf("A", "S", "D", "F", "G", "H", "J", "K", "L")
        val keysRow3 = listOf("Z", "X", "C", "V", "B", "N", "M")

        val rows = listOf(keysRow1, keysRow2, keysRow3)
        Log.d("Keys", rows.size.toString())

        adapter = KeyboardAdapter(rows, ::onLetterClickListener)

        val recyclerView: RecyclerView = binding.rowKeyboardRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        //Letters
        val rowCount = 6 // Количество строк
        val columnCount = 5 // Количество столбцов

        val lettersRecyclerView: RecyclerView = binding.lettersRecyclerView
        lettersRecyclerView.layoutManager = GridLayoutManager(requireContext(), columnCount)
        lettersAdapter = LettersAdapter(requireContext(), rowCount, columnCount)
        lettersRecyclerView.adapter = lettersAdapter

        // Установка случайного слова при старте фрагмента
        setWord(columnCount)

        // Привязка LettersAdapter к GameViewModel
        viewModel.bindLettersAdapter(lettersAdapter)

        viewModel.wordLiveData.observe(viewLifecycleOwner) { word ->
            // Обновляем интерфейс с установленным словом
            // Например, можно использовать word для установки в нужное место в интерфейсе
            Log.d("Game", word)
        }


        // Установка слушателей на кнопки
        binding.deleteButton.setOnClickListener {
            lettersAdapter.deleteLetter()
        }

        binding.nextButton.setOnClickListener {
            // Проверяем, заполнена ли строка, прежде чем переходить к следующей строке
            if (!lettersAdapter.isRowFilled(lettersAdapter.activeRow)) {
                // Ваш код для обработки случая, когда строка не заполнена
                Toast.makeText(requireContext(), "Fill the entire row first", Toast.LENGTH_SHORT).show()
            } else {
                // Переходим к следующей строке и проверяем слово
                val activeRow = lettersAdapter.activeRow
                val activeColumn = lettersAdapter.activeColumn
                lettersAdapter.enableNextRow()
                lettersAdapter.moveToNextCell(activeRow, activeColumn)
                viewModel.checkWordMatch(activeRow)
            }
        }
    }


    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onLetterClickListener(entry: Button) {
        val clickedKey = entry.text.toString()

        if (lettersAdapter != null) {
            val activeRow = lettersAdapter.activeRow
            val activeColumn = lettersAdapter.activeColumn

            // Обновляем букву в текущей активной ячейке и обрабатываем ввод
            lettersAdapter.processLetterInput(activeRow, activeColumn, clickedKey)

            // После обработки ввода, проверим, совпадают ли буквы с исходным словом
            //viewModel.checkWordMatch(activeRow, activeColumn, clickedKey)
        }
    }

    private fun setWord(wordLength: Int) {
        // Здесь вы должны вызвать метод setWord вашего GameViewModel,
        // чтобы получить случайное слово заданной длины и установить его в адаптер
        viewModel.setWord(wordLength)
    }
}