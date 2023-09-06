package com.example.abschlussprojekt.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import com.example.abschlussprojekt.R
import com.example.abschlussprojekt.databinding.FragmentPlantLexiconBinding


class PlantLexiconFragment : Fragment() {
    private val viewModel:ViewModel by activityViewModels()
    private lateinit var binding: FragmentPlantLexiconBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantLexiconBinding.inflate(inflater, container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }
    private fun addObserver(){
        viewModel.viewModelScope
    }
}