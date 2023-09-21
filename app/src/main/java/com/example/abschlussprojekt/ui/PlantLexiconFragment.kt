package com.example.abschlussprojekt.ui

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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


        binding.rvplantLexicon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    // Benutzer hat zum Ende der Liste gescrollt, rufe die nächste Seite ab
                    val currentPage = totalItemCount / PAGE_SIZE + 1 // Annahme: PAGE_SIZE ist die Anzahl der Elemente pro Seite

                    // Hier Daten über ViewModel abrufen
                    viewModel.loadNextPage("oak")
                }
            }
        })
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