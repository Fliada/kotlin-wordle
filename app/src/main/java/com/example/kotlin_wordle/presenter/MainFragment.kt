package com.example.kotlin_wordle.presenter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kotlin_wordle.R
import com.example.kotlin_wordle.databinding.FragmentMainBinding
import com.example.kotlin_wordle.di.ViewModelFactory
import com.example.kotlin_wordle.di.appComponent
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MainViewModel by viewModels() { viewModelFactory }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }
}