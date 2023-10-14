package com.example.abschlussprojekt.ui

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.abschlussprojekt.R
import com.example.abschlussprojekt.ViewModelPackage.fbPhytoVM
import com.example.abschlussprojekt.data.model.PhytoRecipes
import com.example.abschlussprojekt.data.model.cookRecipes
import com.example.abschlussprojekt.databinding.FragmentPhytoDetailsBinding
import com.google.firebase.firestore.ktx.toObjects

class phytoDetailsFragment : Fragment() {
    private val viewModel: fbPhytoVM by activityViewModels()
    private lateinit var binding: FragmentPhytoDetailsBinding
    private var isEditing = false // Um den Bearbeitungsmodus zu verfolgen

    // Erstellen der GetContent-Funktion, um Bilder vom Gerät auszuwählen und anschließend ans ViewModel weiterzugeben
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                viewModel.uploadImage(uri)
            }
        }

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
        viewModel.currentRecipe.observe(viewLifecycleOwner) { recipe ->
            binding.ivPhytoDetail.load(recipe.img)
            binding.tvRecipeNamePhyto.text = Editable.Factory.getInstance().newEditable(recipe.Name)
            binding.tvRecipePhyto.text =
                Editable.Factory.getInstance().newEditable(recipe.description)
        }
        // Funktion um Bild vom Gerät auszuwählen
        binding.upImgPhyto.setOnClickListener {
            getContent.launch("image/*")
        }


        // Initialisieren der Ansicht im Anzeigemodus (nicht im Bearbeitungsmodus)
        setViewInDisplayMode()

        // Klicklistener zum Umschalten zwischen Bearbeitungs- und Anzeigemodus
        binding.buttonEdit.setOnClickListener {
            toggleEditMode()
        }


        /*
        // Edit zurück zu Anzeigemodus speichern
        if (!isEditing) {
            val name = binding.tvRecipeNamePhyto.text.toString()
            val info = binding.tvRecipePhyto.text.toString()
            viewModel.updateRecipe(PhytoRecipes(name, info))
        }*/
        // Neue Daten in Firestore speichern
        binding.SAVEPhyto.setOnClickListener {
            val Name = binding.tvRecipeNamePhyto.text.toString()
            val Info = binding.tvRecipePhyto.text.toString()
            viewModel.updateRecipe(PhytoRecipes(Name, Info))
        }

        // Snapshot Listener: Hört auf Änderungen in dem Firestore Document, das beobachtet wird
        // Hier: Referenz auf Rezept wird beobachtet
        viewModel.recipeRef.addSnapshotListener { snapshot, error ->
            if (error == null && snapshot != null) {
                // Umwandeln des Snapshots in eine Klassen-Instanz und setzen der Felder
                val updatedRecipe = snapshot.toObject(PhytoRecipes::class.java)
                binding.tvRecipeNamePhyto.setText(updatedRecipe?.Name)
                binding.tvRecipePhyto.setText(updatedRecipe?.description)
                if (updatedRecipe?.img != "") {
                    binding.ivPhytoDetail.load(updatedRecipe?.img)
                }
            } else {
                Log.e("snapshot FEHLER", "hier könnte Ihr fehler stehen")
            }
        }

    }

    private fun toggleEditMode() {
        isEditing = !isEditing
        Log.d("EditMode", "isEditing = $isEditing")
        binding.tvRecipeNamePhyto.isEnabled = isEditing
        binding.tvRecipePhyto.isEnabled = isEditing
        binding.SAVEPhyto.visibility = if (isEditing) View.VISIBLE else View.GONE
        binding.upImgPhyto.visibility = if (isEditing) View.VISIBLE else View.GONE
    }
        private fun setViewInDisplayMode() {
            binding.tvRecipeNamePhyto.isEnabled = false
            binding.tvRecipePhyto.isEnabled = false
            binding.SAVEPhyto.visibility = View.GONE
            binding.upImgPhyto.visibility = View.GONE
        }
}
