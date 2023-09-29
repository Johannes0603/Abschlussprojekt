package com.example.abschlussprojekt.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.abschlussprojekt.CookingViewModel
import com.example.abschlussprojekt.R
import com.example.abschlussprojekt.databinding.FragmentCookingBinding
import com.example.abschlussprojekt.databinding.FragmentCookingDetailsBinding
import com.example.abschlussprojekt.databinding.FragmentFavoritesBinding
import com.example.abschlussprojekt.databinding.FragmentPlantLexiconDetailBinding


class cookingDetailsFragment : Fragment() {
    private val viewModel : CookingViewModel by activityViewModels()
    private lateinit var binding: FragmentCookingDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCookingDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}