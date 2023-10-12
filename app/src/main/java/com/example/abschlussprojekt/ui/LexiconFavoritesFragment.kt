package com.example.abschlussprojekt.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.abschlussprojekt.LexiconViewModel
import com.example.abschlussprojekt.R
import com.example.abschlussprojekt.adapter.LexiconAdapter
import com.example.abschlussprojekt.databinding.FragmentLexiconFavoritesBinding


class LexiconFavoritesFragment : Fragment() {
    private val viewModel : LexiconViewModel by activityViewModels()
    private lateinit var binding: FragmentLexiconFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLexiconFavoritesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // var recView = binding.rvLibrary
        // recView.adapter = SearchAdapter(viewModel.listSong, viewModel)
        addObserver()

    }
    private fun addObserver(){
        viewModel.favPlants.observe(viewLifecycleOwner, Observer {
            binding.rvPlantFav.adapter = LexiconAdapter(it, viewModel)
        })
    }

}
