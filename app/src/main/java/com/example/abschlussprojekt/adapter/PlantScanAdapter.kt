package com.example.abschlussprojekt.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussprojekt.data.model.PlantIdentificationResultItem
import com.example.abschlussprojekt.databinding.ListItemBinding

class PlantScanAdapter(private var plants: List<PlantIdentificationResultItem>) :
    RecyclerView.Adapter<PlantScanAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlantIdentificationResultItem) {

            //binding.textViewSpeciesName.text = item.species.scientificName

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = plants[position]
        holder.bind(plant)
    }

    override fun getItemCount(): Int {
        return plants.size
    }

    fun updateData(newPlants: List<PlantIdentificationResultItem>) {
        plants = newPlants
        notifyDataSetChanged()
    }
}