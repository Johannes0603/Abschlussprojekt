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
import com.example.abschlussprojekt.CookingViewModel
import com.example.abschlussprojekt.ViewModelPackage.firebaseCookVM
import com.example.abschlussprojekt.data.model.cookRecipes
import com.example.abschlussprojekt.databinding.FragmentCookingDetailsBinding

class cookingDetailsFragment : Fragment() {
    private val viewModel: firebaseCookVM by activityViewModels()
    private lateinit var binding: FragmentCookingDetailsBinding
    private var isEditing = false // Um den Bearbeitungsmodus zu verfolgen
    // Erstellen der GetContent-Funktion, um Bilder vom Gerät auszuwählen und anschließend ans ViewModel weiterzugeben
   /* private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            CookingViewModel.uploadImage(uri)
        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCookingDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedRecipe.observe(viewLifecycleOwner, {
            // Hier setzen der Daten in die Ansichtselemente
            binding.tvRecipeName.text = Editable.Factory.getInstance().newEditable(it.CookName)
            binding.tvRecipe.text = Editable.Factory.getInstance().newEditable(it.Zubereitung)
            binding.imgCoverDetail.load(it.img)
        })

        // Initialisieren der Ansicht im Anzeigemodus (nicht im Bearbeitungsmodus)
        setViewInDisplayMode()

        // Klicklistener zum Umschalten zwischen Bearbeitungs- und Anzeigemodus
        binding.buttonEdit.setOnClickListener {
            toggleEditMode()
        }

        //Klicklistener zum Speichern von Änderungen
        binding.SAVE.setOnClickListener {
            saveChangesToRecipe()
        }

        binding.upImg.setOnClickListener {
            openImage()
        }
    }

    private fun toggleEditMode() {
        isEditing = !isEditing
        Log.d("EditMode", "isEditing = $isEditing")
        binding.tvRecipeName.isEnabled = isEditing
        binding.tvRecipe.isEnabled = isEditing
        binding.SAVE.visibility = if (isEditing) View.VISIBLE else View.GONE
        binding.upImg.visibility = if (isEditing) View.VISIBLE else View.GONE
    }

    private fun setViewInDisplayMode() {
        binding.tvRecipeName.isEnabled = false
        binding.tvRecipe.isEnabled = false
        binding.SAVE.visibility = View.GONE
        binding.upImg.visibility = View.GONE
    }
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                viewModel.uploadImage(it)
            }
        }
    private fun openImage() {
        getContent.launch("image/*")
    }

    private fun saveChangesToRecipe() {
        // Hier das Rezept in der CookData-Klasse aktualisieren.
        val updatedRecipe = viewModel.selectedRecipe.value ?: return

        // Aktualisierung der Felder im aktualisierten Rezeptobjekt basierend auf den Benutzereingaben
        updatedRecipe.CookName = binding.tvRecipeName.text.toString()
        updatedRecipe.Zubereitung = binding.tvRecipe.text.toString()

        // Speichern des aktualisierten Rezepte in der Datenbank
        viewModel.updateRecipeFire(updatedRecipe)

        // Nach dem Speichern kehre zurück zum Anzeigemodus
        toggleEditMode()
    }
}