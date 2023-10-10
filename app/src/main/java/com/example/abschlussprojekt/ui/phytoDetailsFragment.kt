package com.example.abschlussprojekt.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.abschlussprojekt.R
import com.example.abschlussprojekt.ViewModelPackage.fbPhytoVM
import com.example.abschlussprojekt.databinding.FragmentPhytoDetailsBinding

class phytoDetailsFragment : Fragment() {
    private val viewModel : fbPhytoVM by activityViewModels()
    private lateinit var binding: FragmentPhytoDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhytoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentRecipe.observe(viewLifecycleOwner){
            binding.iv1PhytoDetail.load(it.img)
            binding.tv1NamePhytoDetail.text = it.Name
            binding.tvInfoPhytoDetail.text = it.description
        }
    }


}