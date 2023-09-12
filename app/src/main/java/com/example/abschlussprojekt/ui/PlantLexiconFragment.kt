package com.example.abschlussprojekt.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.abschlussprojekt.LexiconViewModel
import com.example.abschlussprojekt.adapter.LexiconAdapter

import com.example.abschlussprojekt.databinding.FragmentPlantLexiconBinding


class PlantLexiconFragment : Fragment() {
    private val viewModel: LexiconViewModel by activityViewModels()
    private lateinit var binding: FragmentPlantLexiconBinding
    private lateinit var adapter: LexiconAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantLexiconBinding.inflate(inflater, container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeLexiconList()
        viewModel.getPlants("oak")

    }

    private fun setupRecyclerView() {
        adapter = LexiconAdapter(emptyList(), viewModel)
        binding.rvplantLexicon.adapter = adapter
    }

    private fun observeLexiconList() {
        viewModel.lexiconList.observe(viewLifecycleOwner, Observer { plantList ->
            adapter.updateData(plantList)
        })
    }
}