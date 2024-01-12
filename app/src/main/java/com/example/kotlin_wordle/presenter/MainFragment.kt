package com.example.kotlin_wordle.presenter

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kotlin_wordle.R
import com.example.kotlin_wordle.databinding.FragmentMainBinding
import com.example.kotlin_wordle.di.ViewModelFactory
import com.example.kotlin_wordle.di.appComponent
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main), SettingsListListener {

    private val binding: FragmentMainBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MainViewModel by viewModels() { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.start.setOnClickListener {
            onStartClickListener()
        }
    }


    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onStartClickListener() {
        val direction = MainFragmentDirections.actionMainFragmentToGameFragment()
        findNavController().navigate(direction)
    }

    override fun onSettingsClickListener() {
        TODO("Not yet implemented")
    }

    override fun onExitClickListener() {
        TODO("Not yet implemented")
    }
}