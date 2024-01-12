package com.example.kotlin_wordle.presenter.game

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_wordle.databinding.LetterItemBinding
import com.example.kotlin_wordle.databinding.RowKeyboardItemBinding

class LettersAdapter(private val context: Context, private val rowCount: Int, private val columnCount: Int) :
    RecyclerView.Adapter<LettersAdapter.LettersViewHolder>() {

    private val letters: List<MutableList<String>> =
        List(rowCount) { MutableList(columnCount) { "" } }

    public var activeRow = 0
    public var activeColumn = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LettersViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = LetterItemBinding.inflate(layoutInflater, parent, false)
        return LettersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LettersViewHolder, position: Int) {
        val row = position / columnCount
        val col = position % columnCount

        holder.textView.text = letters[row][col]
        holder.textView.isEnabled = row == activeRow
        holder.textView.isFocusable = row == activeRow && col == activeColumn

        holder.textView.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                moveToNextCell(row, col)
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
        } else if (row < rowCount - 1) {
            // Переход к первому элементу в следующей строке
            activeRow = row + 1
            activeColumn = 0
            notifyItemRangeChanged(row * columnCount, columnCount)
        } else {
            // Достигнута последняя строка, ничего не делаем
        }
    }

    class LettersViewHolder(
        private val binding: LetterItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.letterBorder
    }
}