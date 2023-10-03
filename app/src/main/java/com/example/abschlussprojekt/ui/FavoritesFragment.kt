package com.example.abschlussprojekt.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.abschlussprojekt.R
import com.example.abschlussprojekt.databinding.FragmentFavoritesBinding


class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCookingFav.setOnClickListener {
            // Hier zur Ziel-Fragment-Seite (FavoriteFragment) navigieren
            findNavController().navigate(R.id.action_favoritesFragment_to_cookingFavoritesFragment)
        }
        binding.btnPhytoFav.setOnClickListener {
            // Hier zur Ziel-Fragment-Seite (FavoriteFragment) navigieren
            findNavController().navigate(R.id.action_favoritesFragment_to_phytoFavoritesFragment)
        }
        binding.btnLexFav.setOnClickListener {
            // Hier zur Ziel-Fragment-Seite (FavoriteFragment) navigieren
            findNavController().navigate(R.id.action_favoritesFragment_to_lexiconFavoritesFragment)
        }
    }


}