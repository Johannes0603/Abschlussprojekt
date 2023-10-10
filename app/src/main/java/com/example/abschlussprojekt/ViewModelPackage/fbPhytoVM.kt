package com.example.abschlussprojekt.ViewModelPackage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussprojekt.data.model.PhytoRecipes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class fbPhytoVM (application: Application) :
    AndroidViewModel(application) {

    // Instanzen der verschiedenen Firebase Services
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseStore = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()

    private val _currentRecipe = MutableLiveData<PhytoRecipes>()
    val currentRecipe: LiveData<PhytoRecipes>
        get() = _currentRecipe

    fun detailCurrentRecipe(phyto: PhytoRecipes){
        _currentRecipe.value = phyto
    }
}