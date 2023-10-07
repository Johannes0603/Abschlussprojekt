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
import com.google.firebase.storage.UploadTask

class firebaseCookVM(application: Application) : AndroidViewModel(application) {

    // Instanzen der verschiedenen Firebase Services
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseStore = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()

    // Current-User Live-Data
    private var _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    // recipeRef ist lateinit, da sie vom currentUser abhängt
    private var recipeRef: DocumentReference? = null

    // Referenz auf den Firebase Storage
    private val storageRef: StorageReference = firebaseStorage.reference

    private val _selectedRecipe = MutableLiveData<cookRecipes>()
    val selectedRecipe: LiveData<cookRecipes>
        get() = _selectedRecipe

    // Updaten eines Profils im Firestore
    fun updateRecipeFire(updatedRecipe: cookRecipes) {
        recipeRef?.set(updatedRecipe)
        _selectedRecipe.value = updatedRecipe
    }


    // Funktion um Bild in den Firebase Storage hochzuladen
    fun uploadImage(uri: Uri) {
        // Erstellen einer Referenz und des Upload Tasks
        val imageRef = storageRef.child("img/${currentUser.value?.uid}/profilePic")
        val uploadTask: UploadTask = imageRef.putFile(uri)

        // Ausführen des UploadTasks
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                // Wenn Upload erfolgreich, speichern der Bild-Url im User-Profil
                setImage(downloadUri)
            } else {
                Log.e("UploadImage", "Failed to upload image: ${task.exception}")
            }
        }
    }

    // Funktion um Url zu neue hochgeladenem Bild im Firestore upzudaten
    private fun setImage(uri: Uri) {
        recipeRef?.update("img", uri.toString())?.addOnFailureListener {
            Log.w("ERROR", "Error writing document: $it")
        }
    }
}