package com.example.abschlussprojekt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussprojekt.data.model.Plant
import com.example.abschlussprojekt.databinding.ListItemBinding
import com.example.abschlussprojekt.ui.ViewModel

class LexiconAdapter (
    private val dataSet: List<Plant>, val viewModel: ViewModel,
): RecyclerView.Adapter<LexiconAdapter.ItemViewHolder>(){
    inner class ItemViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataSet[position]
        val binding = holder.binding
        binding.imageView.setImageURI(null)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}