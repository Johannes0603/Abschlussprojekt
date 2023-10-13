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
    private val storageRef = firebaseStorage.reference

    private val _selectedRecipe = MutableLiveData<cookRecipes>()
    val selectedRecipe: LiveData<cookRecipes>
        get() {
            if (_selectedRecipe.value == null) {
                _selectedRecipe.value = cookRecipes() // Standard-Rezept erstellen
            }
            return _selectedRecipe
        }

    private val _recipeList = MutableLiveData<MutableList<cookRecipes>>()
    val recipeList: LiveData<MutableList<cookRecipes>>
        get() = _recipeList

    init {
        // Hier die Initialisierung der recipeRef
        val userId = _currentUser.value?.uid ?: ""
        if (userId.isNotEmpty()) {
            recipeRef = firebaseStore.collection("RezepteCook").document(userId)
            // Initialisiere cookRef mit einer sicheren Überprüfung
            cookRef = recipeRef ?: throw IllegalStateException("cookRef could not be initialized")
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

    fun updateRecipe(recipe: cookRecipes) {
        cookRef.set(recipe)
        setSelectedRecipe(recipe)
    }
    //setzen ausgewähltes rezept
    fun setSelectedRecipe(recipe: cookRecipes) {
        _selectedRecipe.value = recipe
    }
    // Funktion um Bild in den Firebase Storage hochzuladen
    fun uploadImage(uri: Uri) {
        if (selectedRecipe.value == null) {
            Log.e("firebaseCookVM", "selectedRecipe.value is null. Cannot upload image.")
            return
        }

        // Erstellen einer Referenz und des Upload Tasks
        val imageRef = storageRef.child("RezepteCook/${selectedRecipe.value!!.userId}/${selectedRecipe.value!!.cookName}/img")
        val uploadTask = imageRef.putFile(uri)

        // Ausführen des UploadTasks
        uploadTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Bild erfolgreich hochgeladen
                imageRef.downloadUrl.addOnCompleteListener { urlTask ->
                    if (urlTask.isSuccessful) {
                        // Wenn Upload erfolgreich, speichern der Bild-Url
                        val imageUrl = urlTask.result.toString()
                        setImage(imageUrl)
                    } else {
                        Log.e("firebaseCookVM", "Failed to get download URL: ${urlTask.exception}")
                    }
                }
            } else {
                Log.e("firebaseCookVM", "Failed to upload image: ${task.exception}")
            }
        }
    }

    // Funktion um Url zu neu hochgeladenem Bild im Firestore zu aktualisieren
    private fun setImage(uri: String) {
        recipeRef?.update("img", uri)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("firebaseCookVM", "Image URL updated successfully.")
            } else {
                Log.e("firebaseCookVM", "Failed to update image URL: ${task.exception}")
            }
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

    //-------------------------------------suche---------------------------------------
    private val _cookList = MutableLiveData<List<cookRecipes>>()
    val inputText = MutableLiveData<String>()

    fun getResultSearch(term: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("RezepteCook")
            .whereEqualTo("cookName", inputText.value)
            .get()
            .addOnSuccessListener { result ->
                val dataList = mutableListOf<cookRecipes>()
                for (document in result) {
                    val cookRecipe = document.toObject(cookRecipes::class.java)
                    dataList.add(cookRecipe)
                }
                _cookList.value = dataList
            }
            .addOnFailureListener { e ->
                Log.e("firebaseCookVM", "Fehler bei der Suche: $e")
            }
    }
}