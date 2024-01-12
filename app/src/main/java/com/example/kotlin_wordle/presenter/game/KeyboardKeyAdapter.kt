package com.example.kotlin_wordle.presenter.game

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_wordle.databinding.ItemKeyboardKeyBinding

class KeyboardKeyAdapter(
    private val keys: List<String>,
    private val onItemClick: (Button) -> Unit,
    private val rowPosition: Int
) : RecyclerView.Adapter<KeyboardKeyAdapter.KeyboardKeyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyboardKeyViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = ItemKeyboardKeyBinding.inflate(layoutInflater, parent, false)
        return KeyboardKeyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KeyboardKeyViewHolder, position: Int) {
        val key = keys[position]
        holder.bind(key, onItemClick, rowPosition)
    }

    override fun getItemCount(): Int {
        return keys.size
    }

    class KeyboardKeyViewHolder(
        private val binding: ItemKeyboardKeyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(key: String, onItemClick: (Button) -> Unit, rowPosition: Int) {
            val button = binding.keyItem
            button.text = key
            button.setOnClickListener {
                onItemClick(button)
                // Дополнительная логика для изменения соответствующего EditText в RecyclerView
                // Ваш код здесь
            }
        }
    }
}