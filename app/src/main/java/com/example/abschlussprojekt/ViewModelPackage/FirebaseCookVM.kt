package com.example.abschlussprojekt.ViewModelPackage

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussprojekt.data.model.cookRecipes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class firebaseCookVM(application: Application) : AndroidViewModel(application) {

    // Instanzen der verschiedenen Firebase Services
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseStore = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()

    lateinit var cookRef: DocumentReference

    // Current-User Live-Data
    private var _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    private var recipeRef: DocumentReference? = null

    // Referenz auf den Firebase Storage
    private val storageRef: StorageReference = firebaseStorage.reference

    private val _selectedRecipe = MutableLiveData<cookRecipes>()
    val selectedRecipe: LiveData<cookRecipes>
        get() = _selectedRecipe

    private val _recipeList = MutableLiveData<MutableList<cookRecipes>>()
    val recipeList: LiveData<MutableList<cookRecipes>>
        get() = _recipeList

    fun updateRecipe(recipe: cookRecipes) {
        cookRef.set(recipe)
    }

    init {
        // Hier die Initialisierung der recipeRef
        val userId = _currentUser.value?.uid ?: ""
        if (userId.isNotEmpty()) {
            recipeRef = firebaseStore.collection("RezepteCook").document(userId)
        }
        // Hier die Initialisierung der Rezeptliste
        loadRecipeList()
    }

    private fun loadRecipeList() {
        // Lade die Rezepte aus Firebase und aktualisiere die _recipeList
        val userId = _currentUser.value?.uid ?: ""
        if (userId.isNotEmpty()) {
            firebaseStore.collection("RezepteCook")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { result ->
                    val recipes = mutableListOf<cookRecipes>()
                    for (document in result) {
                        val recipe = document.toObject(cookRecipes::class.java)
                        recipes.add(recipe)
                    }
                    _recipeList.value = recipes
                }
                .addOnFailureListener { exception ->
                    Log.w("Firebase", "Error getting documents: ", exception)
                }
        }
    }

    // Updaten eines Rezepts im Firestore

    fun updateRecipeFire(updatedRecipe: cookRecipes) {
        recipeRef?.set(updatedRecipe)
        _selectedRecipe.value = updatedRecipe
    }


    // Funktion um Bild in den Firebase Storage hochzuladen
    fun uploadImage(uri: Uri) {
        // Erstellen einer Referenz und des Upload Tasks
        val imageRef = storageRef.child("RezepteCook/5gPs86f5sfMsN69FGYL6/img")
        val uploadTask = imageRef.putFile(uri)

        // Ausführen des UploadTasks
        // Ausführen des UploadTasks
        uploadTask.addOnCompleteListener {
            imageRef.downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {
                    // Wenn Upload erfolgreich, speichern der Bild-Url im User-Profil
                    setImage(it.result)
                }
            }
        }
    }

    // Funktion um Url zu neu hochgeladenem Bild im Firestore upzudaten
    private fun setImage(uri: Uri) {
        recipeRef?.update("img", uri.toString())?.addOnFailureListener {
            Log.w("ERROR", "Error writing document: $it")
        }
    }

    // Hinzufügen eines neuen Rezepts zu Firebase
    fun addNewRecipeToFirebase(newRecipe: cookRecipes) {
        val collectionRef = firebaseStore.collection("RezepteCook")
        val newRecipeRef = collectionRef.document()
        newRecipe.userId = newRecipeRef.id // Setzen der automatisch generierten ID
        newRecipeRef.set(newRecipe)
            .addOnSuccessListener {
                Log.d("firebaseCookVM", "Neues Rezept wurde erfolgreich hinzugefügt")
            }
            .addOnFailureListener { e ->
                Log.e("firebaseCookVM", "Fehler beim Hinzufügen des neuen Rezepts", e)
            }
    }

    /*
    // Hinzufügen eines neuen Rezepts zu Firebase
    fun addNewRecipe(newRecipe: cookRecipes) {
        //   Rezept zu Firebase hinzufügen
        val userId = _currentUser.value?.uid ?: ""
        if (userId.isNotEmpty()) {
            firebaseStore.collection("RezepteCook")
                .add(newRecipe.copy(userId = userId))
                .addOnSuccessListener { documentReference ->
                    Log.d("Firebase", "Rezept wurde mit ID: ${documentReference.id} hinzugefügt")
                    //lokale Liste Aktualisieren
                    loadRecipeList()
                }
                .addOnFailureListener { e ->
                    Log.w("Firebase", "Fehler beim Hinzufügen des Rezepts", e)
                }
        }
    }*/
    //-------------------------------------suche---------------------------------------
    private val _cookList = MutableLiveData<List<cookRecipes>>()
    val inputText = MutableLiveData<String>()
    fun getResultSearch(term: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("RezepteCook")
            .whereEqualTo("cookName", inputText).get().addOnSuccessListener {
                val dataList = mutableListOf<cookRecipes>()
                for (it in it) {
                    val cookRecipe = it.toObject(cookRecipes::class.java)
                    dataList.add(cookRecipe)
                }
                _cookList.value = dataList
            }
            .addOnFailureListener {
                Log.d("UIUIUIUI","hier könnte Ihr Fehler stehen")
            }
    }
}

