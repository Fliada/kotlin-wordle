package com.example.kotlin_wordle.presenter.game

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kotlin_wordle.R
import com.example.kotlin_wordle.databinding.FragmentGameBinding
import com.example.kotlin_wordle.databinding.FragmentMainBinding
import com.example.kotlin_wordle.di.ViewModelFactory
import com.example.kotlin_wordle.di.appComponent
import com.example.kotlin_wordle.presenter.MainViewModel
import javax.inject.Inject

class GameFragment : Fragment() {
    private val binding: FragmentGameBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: GameViewModel by viewModels() { viewModelFactory }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }
}