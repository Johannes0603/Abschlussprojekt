package com.example.abschlussprojekt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussprojekt.CookingViewModel
import com.example.abschlussprojekt.data.model.cookRecipes
import com.example.abschlussprojekt.databinding.ListItemBinding

class cookingAdapter (
    private var dataSet: List<cookRecipes>
) : RecyclerView.Adapter<cookingAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataSet[position]
        val binding = holder.binding
        binding.tvListItem.text = item.name
        binding.ivLexiconList.setImageResource(item.image)

}
    override fun getItemCount(): Int {
        return dataSet.size
    }
}