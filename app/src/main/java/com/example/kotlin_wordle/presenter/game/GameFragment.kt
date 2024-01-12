package com.example.kotlin_wordle.presenter.game

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.fragment.app.viewModels
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
    }


    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onLetterClickListener(entry : Button) {
        val clickedKey = entry.text.toString()
        // Обработайте нажатие кнопки, например, добавьте текст в ваш EditText
        binding.editText.append(clickedKey)
    }
}