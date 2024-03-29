package com.example.kotlin_wordle.presenter.game

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_wordle.R
import com.example.kotlin_wordle.databinding.ItemKeyboardKeyBinding
import com.example.kotlin_wordle.databinding.RowKeyboardItemBinding

class KeyboardAdapter(
    private val rows: List<List<String>>,
    private val onItemClick: (Button) -> Unit
) : RecyclerView.Adapter<KeyboardAdapter.KeyboardRowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyboardRowViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = RowKeyboardItemBinding.inflate(layoutInflater, parent, false)
        return KeyboardRowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KeyboardRowViewHolder, position: Int) {
        val keys = rows[position]
        val keyAdapter = KeyboardKeyAdapter(keys, onItemClick, position)
        holder.bind(keyAdapter)
    }

    override fun getItemCount(): Int {
        return rows.size
    }

    class KeyboardRowViewHolder(
        private val binding: RowKeyboardItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(adapter: RecyclerView.Adapter<KeyboardKeyAdapter.KeyboardKeyViewHolder>) {
            val recyclerView: RecyclerView = binding.keyboardRecyclerView
            recyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = adapter
        }
    }
}