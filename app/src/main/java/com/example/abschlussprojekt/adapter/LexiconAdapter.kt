package com.example.abschlussprojekt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussprojekt.LexiconViewModel
import com.example.abschlussprojekt.data.model.Plant
import com.example.abschlussprojekt.databinding.ListItemBinding

class LexiconAdapter(
    private var dataSet: List<Plant>,
    private val viewModel: LexiconViewModel
) : RecyclerView.Adapter<LexiconAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataSet[position]
        val binding = holder.binding
        binding.imageView.setImageURI(null)
        binding.nameTextView.text = item.commonName
        viewModel
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun updateData(newData: List<Plant>) {
        dataSet = newData
        notifyDataSetChanged()
    }
}