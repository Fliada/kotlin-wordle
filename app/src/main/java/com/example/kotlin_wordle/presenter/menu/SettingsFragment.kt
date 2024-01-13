package com.example.kotlin_wordle.presenter.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.example.kotlin_wordle.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {

    private lateinit var viewModel: SettingsViewModel // Предположим, что у вас есть ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = SettingsViewModel()
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val themeSpinner: Spinner = view.findViewById(R.id.themeSpinner)
        val difficultySpinner: Spinner = view.findViewById(R.id.difficultySpinner)
        val defaultButton: Button = view.findViewById(R.id.defaultButton)
        val okButton: Button = view.findViewById(R.id.okButton)

        // Настройте адаптеры для спиннеров
        val themeAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            listOf("Light", "Dark")
        )
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        themeSpinner.adapter = themeAdapter

        val difficultyAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            listOf("3", "4", "5", "6")
        )
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        difficultySpinner.adapter = difficultyAdapter

        defaultButton.setOnClickListener {
            // Обработка нажатия на кнопку "Default"
            // Установите значения по умолчанию
            themeSpinner.setSelection(0) // Установка светлой темы
            difficultySpinner.setSelection(2) // Установка значения 5
        }

        okButton.setOnClickListener {
            // Обработка нажатия на кнопку "OK"
            // Примените выбранные настройки

            val selectedTheme = themeSpinner.selectedItem.toString()
            val selectedDifficulty = difficultySpinner.selectedItem.toString()

            // Передайте значения во ViewModel
            viewModel.setTheme(selectedTheme)
            viewModel.setDifficulty(selectedDifficulty)

            // Закройте фрагмент
            fragmentManager?.popBackStack()
        }
    }
}