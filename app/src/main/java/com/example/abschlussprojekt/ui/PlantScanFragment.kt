package com.example.abschlussprojekt.uiimport

import com.example.abschlussprojekt.Manifest
import com.example.abschlussprojekt.adapter.PlantScanAdapter


import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abschlussprojekt.databinding.FragmentPlantScanBinding
import com.example.abschlussprojekt.ViewModelPackage.PlantIdentificationVM

class PlantScanFragment : Fragment() {
    private lateinit var binding: FragmentPlantScanBinding
    private lateinit var adapter: PlantScanAdapter
    private val viewModel: PlantIdentificationVM by activityViewModels()

    private val CAMERA_PERMISSION_REQUEST = 101

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = PlantScanAdapter(emptyList())
        binding.recyclerViewPlants.adapter = adapter
        binding.recyclerViewPlants.layoutManager = LinearLayoutManager(requireContext())

        // Behandlung des Scan-Buttons
        binding.buttonTestScan.setOnClickListener {
            if (hasCameraPermission()) {
                // Kamera-Aktivität oder Kamera-Fragment hier
                // Erfasse ein Bild und führe Bilderkennung aus
                // Aktualisierte UI mit den erkannten Pflanzen
            } else {
                requestCameraPermission()
            }
        }

        // Beobachte die Ergebnisse und aktualisiere den Adapter
        viewModel.plantIdentificationResult.observe(viewLifecycleOwner, Observer { result ->
            adapter.updateData(result)
        })
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST
        )
    }
}
