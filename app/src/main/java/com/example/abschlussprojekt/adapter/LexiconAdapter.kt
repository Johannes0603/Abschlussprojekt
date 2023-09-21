package com.example.abschlussprojekt.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.abschlussprojekt.LexiconViewModel
import com.example.abschlussprojekt.R
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
        val imgUri = item.imageUrl?.toUri()?.buildUpon()?.scheme("https")?.build()
        //binding.imageView.setImageURI(null)
        binding.tvNameLexiconList.text = item.commonName
        loadRoundImage(binding.ivLexiconList, imgUri) // Hier wird die Methode loadRoundImage aufgerufen
        binding.btnListItem.setOnClickListener{
            viewModel.detailCurrentPlant(item)
            val navController = holder.itemView.findNavController()
            navController.navigate(R.id.plantLexiconDetailFragment)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun updateData(newData: List<Plant>) {
        dataSet = newData
        notifyDataSetChanged()
    }

    // Methode zum Laden des Bildes in das ImageView im runden Format
    private fun loadRoundImage(imageView: ImageView, imageUrl: Uri?) {
        Glide.with(imageView)
            .load(imageUrl)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(imageView)
    }
}

/*
notwendige importe:
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions

gradle(module:app)
groovy
dependencies {


    implementation 'com.github.bumptech.glide:glide:4.12.0'
    kapt 'com.github.bumptech.glide:compiler:4.12.0'
}*/