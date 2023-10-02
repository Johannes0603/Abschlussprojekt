package com.example.abschlussprojekt.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.abschlussprojekt.LexiconViewModel
import com.example.abschlussprojekt.R
import com.example.abschlussprojekt.databinding.FragmentPlantLexiconDetailBinding


class PlantLexiconDetailFragment : Fragment() {
    private val viewModel : LexiconViewModel by activityViewModels()
    private lateinit var binding: FragmentPlantLexiconDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlantLexiconDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentPlant.observe(viewLifecycleOwner){
            binding.iv1LexiconDetail.load(it.imageUrl)
            binding.tv1LexiconDetail.text = it.commonName
            binding.tv2LexiconDetail.text = it.scientificName
        }
        val toggleButton = binding.toggleButton
        toggleButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // Wenn der ToggleButton ausgewählt ist (ausgefüllter Stern), ändere das Hintergrundbild
                buttonView.setBackgroundResource(R.drawable.btn_fav_on)
            } else {
                // Wenn der ToggleButton nicht ausgewählt ist (leerer Stern), ändere das Hintergrundbild
                buttonView.setBackgroundResource(R.drawable.btn_fav_off)
            }
        }
    }
}