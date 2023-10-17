package com.example.abschlussprojekt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussprojekt.CookingViewModel
import com.example.abschlussprojekt.R
import com.example.abschlussprojekt.data.model.RecipeData
import com.example.abschlussprojekt.databinding.ListItemBinding

class cookingAdapter (
    private var dataSet: List<RecipeData>,
    private val viewModel: CookingViewModel
) : RecyclerView.Adapter<cookingAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataSet[position]
        val binding = holder.binding
        val imgUri = item.image
        binding.tvListItem.text = item.title
        //hier coil transform

        binding.btnListItem.setOnClickListener{
            viewModel.detailCurrentRecipe(item)
            val navController = holder.itemView.findNavController()
            navController.navigate(R.id.cookingDetailsFragment)
        }


}
    override fun getItemCount(): Int {
        return dataSet.size
    }

}