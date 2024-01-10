package com.example.kotlin_wordle.presenter

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kotlin_wordle.R
import com.example.kotlin_wordle.databinding.FragmentMainBinding

class SampleFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding()
    private val viewModel: MainViewModel by viewModels()

}