package com.example.abschlussprojekt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.abschlussprojekt.CookingViewModel
import com.example.abschlussprojekt.data.dataclass.RecipeData
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
        loadRoundImage(binding.ivLexiconList, imgUri)


}
    override fun getItemCount(): Int {
        return dataSet.size
    }
    private fun loadRoundImage(imageView: ImageView, imageUrl: Int) {
        Glide.with(imageView)
            .load(imageUrl)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(imageView)
    }
}