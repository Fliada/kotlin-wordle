package com.example.kotlin_wordle.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(
    private val onItemClick: (Button) -> Unit,
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val list = mutableListOf<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val context = parent.context
        val button = Button(context)
        return MainViewHolder(button, onItemClick)
    }

    override fun getItemCount(): Int =
        list.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.button.text = list[position]
    }

    fun submitList(list: List<String>) {
        with(this.list) {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    class MainViewHolder(
        val button: Button,
        private val onItemClick: (Button) -> Unit,
    ) : RecyclerView.ViewHolder(button) {
        init {
            button.setOnClickListener {
                onItemClick(button)
            }
        }
    }
}
