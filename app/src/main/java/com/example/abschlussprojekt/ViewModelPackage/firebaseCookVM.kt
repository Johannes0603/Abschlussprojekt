package com.example.abschlussprojekt.ViewModelPackage

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussprojekt.data.model.RecipeData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class firebaseCookVM (application: Application) : AndroidViewModel(application){

    // Instanzen der verschiedenen Firebase Services
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseStore = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()

    // Current-User Live-Data
    private var _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    // profileRef ist lateinit, da sie vom currentUser abhängt, wird in Funktion setupUserEnv() gesetzt sobald User eingeloggt wird
    lateinit var profileRef: DocumentReference

    // Referenz auf den Firebase Storage
    private val storageRef = firebaseStorage.reference

    // Updaten eines Profils im Firestore
    fun updateRecipe(upRecipe: RecipeData) {
        profileRef.set(upRecipe)
    }

    // Funktion um Bild in den Firebase Storage hochzuladen
    fun uploadImage(uri: Uri) {
        // Erstellen einer Referenz und des Upload Tasks
        val imageRef = storageRef.child("images/${currentUser.value?.uid}/profilePic")
        val uploadTask = imageRef.putFile(uri)

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

    // Funktion um Url zu neue hochgeladenem Bild im Firestore upzudaten
    private fun setImage(uri: Uri) {
        profileRef.update("profilePicture", uri.toString()).addOnFailureListener {
            Log.w("ERROR", "Error writing document: $it")
        }
    }
}