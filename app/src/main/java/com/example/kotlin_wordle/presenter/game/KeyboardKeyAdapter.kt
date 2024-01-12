package com.example.kotlin_wordle.presenter.game

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_wordle.databinding.ItemKeyboardKeyBinding

class KeyboardKeyAdapter(private val keys: List<String>,
                         private val onItemClick: (Button) -> Unit
) : RecyclerView.Adapter<KeyboardKeyAdapter.KeyboardKeyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyboardKeyViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = ItemKeyboardKeyBinding.inflate(layoutInflater, parent, false)
        return KeyboardKeyViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: KeyboardKeyViewHolder, position: Int) {
        Log.d("KeyboardKeyAdapter", "I'm here")
        val key = keys[position]
        holder.bind(key)
    }

    override fun getItemCount(): Int {
        return keys.size
    }

    class KeyboardKeyViewHolder(
        private val binding: ItemKeyboardKeyBinding,
        private val onItemClick: (Button) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(key: String) = with(binding) {
            val button = itemView as Button
            button.text = key

            root.setOnClickListener {
                onItemClick(button)
            }
        }
    }
}