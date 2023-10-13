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
    // Erstellen der GetContent-Funktion, um Bilder vom Gerät auszuwählen und anschließend ans ViewModel weiterzugeben
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            viewModel.uploadImage(uri)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCookingDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedRecipe.observe(viewLifecycleOwner, {recipe ->
            // Hier setzen der Daten in die Ansichtselemente
            binding.tvRecipeName.text = Editable.Factory.getInstance().newEditable(recipe.cookName)
            binding.tvRecipe.text = Editable.Factory.getInstance().newEditable(recipe.Zubereitung)
            binding.imgCoverDetail.load(recipe.img)
        })

// Funktion um Bild vom Gerät auszuwählen
        binding.upImg.setOnClickListener {
            getContent.launch("image/*")
            //openImage()

        }

        // Initialisieren der Ansicht im Anzeigemodus (nicht im Bearbeitungsmodus)
        setViewInDisplayMode()

        // Klicklistener zum Umschalten zwischen Bearbeitungs- und Anzeigemodus
        binding.buttonEdit.setOnClickListener {
            toggleEditMode()
        }
        //Klicklistener zum Speichern von Änderungen
        binding.SAVE.setOnClickListener {
            val name = binding.tvRecipeName.text.toString()
            val info = binding.tvRecipe.text.toString()
            viewModel.updateRecipe(cookRecipes(name, info))
        }
// Snapshot Listener: Hört auf Änderungen in dem Firestore Document, das beobachtet wird
        // Hier: Referenz auf Profil wird beobachtet
        viewModel.cookRef.addSnapshotListener { snapshot, error ->
            if (error == null && snapshot != null){
                // Umwandeln des Snapshots in eine Klassen-Instanz und setzen der Felder
                val updatedRecipe = snapshot.toObject(cookRecipes::class.java)
                binding.tvRecipeName.setText(updatedRecipe?.cookName)
                binding.tvRecipe.setText(updatedRecipe?.Zubereitung)
                if (updatedRecipe?.img != ""){
                    binding.imgCoverDetail.load(updatedRecipe?.img)
                }
            }else {
                Log.e("snapshot FEHLER", "hier könnte Ihr fehler stehen")
            }
        }
    }

    private fun toggleEditMode() {
        isEditing = !isEditing
        Log.d("EditMode", "isEditing = $isEditing")
        binding.tvRecipeName.isEnabled = isEditing
        binding.tvRecipe.isEnabled = isEditing
        binding.SAVE.visibility = if (isEditing) View.VISIBLE else View.GONE
        binding.upImg.visibility = if (isEditing) View.VISIBLE else View.GONE


        // Edit zurück zu Anzeigemodus speichern
        if (!isEditing) {
            val name = binding.tvRecipeName.text.toString()
            val info = binding.tvRecipe.text.toString()
            viewModel.updateRecipe(cookRecipes(name, info))
        }
    }

    private fun setViewInDisplayMode() {
        binding.tvRecipeName.isEnabled = false
        binding.tvRecipe.isEnabled = false
        binding.SAVE.visibility = View.GONE
        binding.upImg.visibility = View.GONE
    }


    }
/*
    private fun saveChangesToRecipe() {
        // Hier das Rezept in der CookData-Klasse aktualisieren.
        val updatedRecipe = viewModel.selectedRecipe.value ?: return

        // Aktualisierung der Felder im aktualisierten Rezeptobjekt basierend auf den Benutzereingaben
        updatedRecipe.cookName = binding.tvRecipeName.text.toString()
        updatedRecipe.zubereitung = binding.tvRecipe.text.toString()

        //

        updatedRecipe.img = binding.imgCoverDetail.load("").toString()
        /*
        val imageResourceId = resources.getIdentifier(updatedRecipe.img, "drawable", "")
        binding.imgCoverDetail.setImageResource(imageResourceId)*/
        //

        // Speichern des aktualisierten Rezepts in der Datenbank (oder Hinzufügen eines neuen Rezepts)
        if (updatedRecipe.userId.isEmpty()) {
            // Hinzufügen eines neuen Rezepts
            viewModel.addNewRecipeToFirebase(updatedRecipe)
        } else {
            // Aktualisieren eines vorhandenen Rezepts
            viewModel.updateRecipeFire(updatedRecipe)
        }

        // Nach dem Speichern  zurück zum Anzeigemodus
        toggleEditMode()
    }*/
