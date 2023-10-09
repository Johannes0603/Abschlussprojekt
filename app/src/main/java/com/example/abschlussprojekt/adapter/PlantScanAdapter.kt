package com.example.abschlussprojekt.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussprojekt.data.model.PlantIdentificationResultItem
import com.example.abschlussprojekt.databinding.ListItemBinding

class PlantScanAdapter(private var dataSet: List<PlantIdentificationResultItem>) :
    RecyclerView.Adapter<PlantScanAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlantIdentificationResultItem) {

            //binding.textViewSpeciesName.text = item.species.scientificName

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val plant = dataSet[position]
        holder.bind(plant)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun updateData(newPlants: List<PlantIdentificationResultItem>) {
        dataSet = newPlants
        notifyDataSetChanged()
    }
}