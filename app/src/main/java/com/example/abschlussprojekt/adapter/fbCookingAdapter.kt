package com.example.abschlussprojekt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.abschlussprojekt.ViewModelPackage.firebaseCookVM
import com.example.abschlussprojekt.data.model.cookRecipes
import com.example.abschlussprojekt.databinding.ListItemBinding

class fbCookingAdapter(  private var dataSet: List<cookRecipes>,private val viewModel : firebaseCookVM):
    RecyclerView.Adapter<fbCookingAdapter.ItemViewHolder>() {
    private var onItemClickListener: ((cookRecipes) -> Unit)? = null

    inner class ItemViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: fbCookingAdapter.ItemViewHolder, position: Int){
        val item = dataSet[position]
        val binding = holder.binding
        val imgUri = item.img.toUri()?.buildUpon()?.scheme("")?.build()
        binding.tvListItem.text = item.cookName
        binding.ivLexiconList.load(item.img){
            //hier coil transform
            transformations(CircleCropTransformation())

        }
        //loadRoundImage(binding.ivLexiconList, imgUri)


        // Hier setzen wir den Click-Listener auf das Element
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(item)
        }

    }
    override fun getItemCount(): Int {
        return dataSet.size
    }
    fun setOnItemClickListener(listener: (cookRecipes) -> Unit) {
        onItemClickListener = listener
    }
    /*
    // Methode zum Laden des Bildes in das ImageView im runden Format
    private fun loadRoundImage(imageView: ImageView, imageUrl: Uri?) {
        Glide.with(imageView)
            .load(imageUrl)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(imageView)
    }*/


}