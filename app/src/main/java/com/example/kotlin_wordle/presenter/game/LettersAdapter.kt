package com.example.kotlin_wordle.presenter.game

import android.content.Context
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_wordle.R
import com.example.kotlin_wordle.databinding.LetterItemBinding

class LettersAdapter(private val context: Context, private val rowCount: Int, private val columnCount: Int) :
    RecyclerView.Adapter<LettersAdapter.LettersViewHolder>() {

    private val letters: List<MutableList<String>> =
        List(rowCount) { MutableList(columnCount) { "" } }

    // Добавляем дополнительное поле для хранения состояния ячейки
    private val cellStates: List<MutableList<CellState>> =
        List(rowCount) { MutableList(columnCount) { CellState() } }

    public var activeRow = 0
    public var activeColumn = 0
    private var wordLetters: List<String> = emptyList()
    public var wantedWord: List<Char> = emptyList()

    private val enteredWord: MutableList<String> = MutableList(columnCount) { "" }

    // Флаг, определяющий, можно ли активировать следующую строку
    private var isNextRowEnabled = false

    fun enableNextRow() {
        isNextRowEnabled = true
    }

    fun getEnteredWord(): List<String> {
        return enteredWord.toList()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LettersViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = LetterItemBinding.inflate(layoutInflater, parent, false)
        return LettersViewHolder(binding)
    }

    fun deleteLetter() {
        // Удаляем букву из текущей активной ячейки
        updateLetter(activeRow, activeColumn, "")
        if(activeColumn != 0)
            activeColumn -= 1
    }

    fun isRowFilled(row: Int): Boolean {
        // Проверяем, заполнена ли строка
        return letters[row].none { it.isBlank() }
    }

    private fun setCellBackgroundColor(textView: TextView, isCorrect: Boolean, isUserInput: Boolean) {
        if (isUserInput) {
            textView.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    if (isCorrect) R.color.green else R.color.yellow
                )
            )
        }
    }

    fun moveToNextRow() {
        if (activeRow < rowCount - 1 && isNextRowEnabled) {
            // Переход к следующей строке, если флаг разрешает
            activeRow += 1
            activeColumn = 0
            notifyItemRangeChanged((activeRow - 1) * columnCount, columnCount)
        }
        isNextRowEnabled = false
    }

    override fun onBindViewHolder(holder: LettersViewHolder, position: Int) {
        val row = position / columnCount
        val col = position % columnCount

        val cellState = cellStates[row][col]

        holder.bind(cellState)

        holder.textView.text = letters[row][col]
        holder.textView.isEnabled = row == activeRow
        holder.textView.isFocusable = row == activeRow && col == activeColumn

        // Отображение букв слова
        if (wordLetters.isNotEmpty() && row == activeRow) {
            holder.textView.text = wordLetters.getOrNull(col) ?: ""
        }

        val (isFocused, isCorrect, isUserInput) = Triple(false, false, false)  // или создайте объект CellInfo с нужными значениями

        // Установка фона в зависимости от совпадения буквы
        if (isFocused) {
            when {
                isCorrect -> {
                    setCellBackgroundColor(holder.textView, true, true)
                }
                isUserInput -> {
                    setCellBackgroundColor(holder.textView, false, true)
                }
                else -> {
                    // Сброс цвета фона, если не пользовательский ввод
                    setCellBackgroundColor(holder.textView, false, false)
                }
            }
        }

        holder.textView.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                moveToNextRow()  // Активация следующей строки
                return@setOnKeyListener true
            }
            false
        }

        holder.textView.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                // Вызываем метод удаления буквы при нажатии на "Backspace"
                deleteLetter()
                return@setOnKeyListener true
            }
            false
        }

        holder.textView.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                activeRow = row
                activeColumn = col
            }
        }
    }

    override fun getItemCount(): Int {
        return rowCount * columnCount
    }

    fun updateLetter(row: Int, col: Int, letter: String) {
        if (row in 0 until rowCount && col in 0 until columnCount) {
            letters[row][col] = letter
            notifyItemChanged(row * columnCount + col)
        }
    }

    fun moveToNextCell(row: Int, col: Int) {
        if (col < columnCount - 1) {
            // Переход к следующему элементу в строке
            activeColumn = col + 1
            notifyItemChanged(row * columnCount + col + 1)
        } else if (row < rowCount - 1 && isNextRowEnabled) {
            // Переход к первому элементу в следующей строке
            activeRow = row + 1
            activeColumn = 0
            isNextRowEnabled = false
            notifyItemRangeChanged(row * columnCount, columnCount)
        } else {
            // Достигнута последняя строка, ничего не делаем
        }

        // Сброс цвета фона в предыдущей активной ячейке
        //notifyItemChanged(row * columnCount + col, Triple(false, false, true))
    }

    fun updateCellAppearance(row: Int, col: Int, isCorrect: Boolean, isFocused: Boolean, isUserInput: Boolean) {
        Log.d("LettersAdapter", "Update cell at position ${row * columnCount + col} - Correct: $isCorrect, Focused: $isFocused")
        updateCellState(row, col, CellState(isFocused = isFocused, isCorrect = isCorrect, isUserInput = isUserInput))
        notifyItemChanged(row * columnCount + col, Triple(isFocused, isCorrect, isUserInput))
    }

    fun updateCellState(row: Int, col: Int, cellState: CellState) {
        if (row in 0 until rowCount && col in 0 until columnCount) {
            cellStates[row][col] = cellState
            notifyItemChanged(row * columnCount + col)
        }
    }


    // Новый метод для обработки ввода буквы
    fun processLetterInput(row: Int, col: Int, letter: String) {
        if (row in 0 until rowCount && col in 0 until columnCount) {
            val isCorrectLetter = wantedWord.getOrNull(col)?.toUpperCase() == letter.toCharArray()[0]
            updateLetter(row, col, letter)

            // Обновление буквы в списке введенных букв
            enteredWord[col] = letter

            val position = row * columnCount + col
            Log.d("LettersAdapter", "Process input at position $position - Correct: $isCorrectLetter")

            // Переход к следующей ячейке
            moveToNextCell(row, col)
            notifyDataSetChanged()
        }
    }

    class LettersViewHolder(
        private val binding: LetterItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.letterBorder

        // Используем CellState для хранения состояния ячейки
        private var cellState: CellState? = null

        // Новый метод для обновления вида ячейки с буквой
        fun bind(cellState: CellState) {
            this.cellState = cellState

            // Установка фона в зависимости от совпадения буквы
            if (cellState?.isFocused == true) {
                when {
                    cellState?.isCorrect == true -> {
                        textView.setBackgroundColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.green
                            )
                        ) // Зеленый фон
                    }
                    cellState?.isUserInput == true -> {
                        textView.setBackgroundColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.yellow
                            )
                        ) // Желтый фон
                    }
                }
            }
        }
    }

    // Добавляем класс для представления состояния ячейки
    data class CellState(
        val isFocused: Boolean = false,
        val isCorrect: Boolean = false,
        val isUserInput: Boolean = false
    )
}