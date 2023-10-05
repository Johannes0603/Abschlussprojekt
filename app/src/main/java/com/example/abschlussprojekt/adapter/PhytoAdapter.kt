package com.example.abschlussprojekt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussprojekt.ViewModelPackage.fbPhytoVM
import com.example.abschlussprojekt.data.model.PhytoRecipes
import com.example.abschlussprojekt.databinding.ListItemBinding

class PhytoAdapter(
    private var dataSet: List<PhytoRecipes>,
    private val viewModel : fbPhytoVM)
    : RecyclerView.Adapter<PhytoAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhytoAdapter.ItemViewHolder, position: Int){
        val item = dataSet[position]
        val binding = holder.binding
    }
    override fun getItemCount(): Int {
        return dataSet.size
    }

}