package com.example.abschlussprojekt.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.abschlussprojekt.R
import com.example.abschlussprojekt.ViewModelPackage.fbPhytoVM
import com.example.abschlussprojekt.data.model.PhytoRecipes
import com.example.abschlussprojekt.databinding.ListItemBinding

class PhytoAdapter(
    private var dataSet: List<PhytoRecipes>,
    private val viewModel : fbPhytoVM)
    : RecyclerView.Adapter<PhytoAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhytoAdapter.ItemViewHolder, position: Int) {
        val item = dataSet[position]
        val binding = holder.binding
        val imgUri = item.img.toUri()?.buildUpon()?.scheme("")?.build()
        binding.tvListItem.text = item.Name
        binding.ivLexiconList.load(item.img){
            transformations(CircleCropTransformation())
        }

        binding.btnListItem.setOnClickListener {
            viewModel.detailCurrentRecipe(item)
            Log.d("ed", "$item")
            val navController = holder.itemView.findNavController()
            navController.navigate(R.id.phytoDetailsFragment)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}

    /*
    // Methode zum Laden des Bildes in das ImageView im runden Format
    private fun loadRoundImage(imageView: ImageView, imageUrl: Uri?) {
        Glide.with(imageView)
            .load(imageUrl)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(imageView)
    }*/

