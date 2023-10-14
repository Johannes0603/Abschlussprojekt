package com.example.abschlussprojekt.ViewModelPackage

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussprojekt.data.model.PhytoRecipes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
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
    // Current-User Live-Data
    private var _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    // profileRef ist lateinit, da sie vom currentUser abhängt, wird in Funktion setupUserEnv() gesetzt sobald User eingeloggt wird

    // Referenz auf den Firebase Storage
    private val storageRef = firebaseStorage.reference
    lateinit var recipeRef: DocumentReference

    // Statische Referenz auf die Rezepte Collection
    val phytoRef = firebaseStore.collection("RezeptePhyt")

  // "Cookie-Funktion": Wenn ein User die App startet und bereits eingeloggt ist
init {
  if (firebaseAuth.currentUser != null) {
      setupUserEnv()
  }
}

// Setzdie nötigen Werte sobald User eingeloggt ist
private fun setupUserEnv() {
  _currentUser.value = firebaseAuth.currentUser
  recipeRef = firebaseStore.collection("RezeptePhyt").document(firebaseAuth.currentUser?.uid!!)
}


// Erstellen eines neuen Rezepts mit leeren Werten im Firestore
private fun setupNewRecipe() {
  recipeRef.set(PhytoRecipes())
}
    fun updateRecipe(recipe: PhytoRecipes) {
        recipeRef.update("Name", recipe.Name, "description", recipe.description)
    }
// Speichern eines Rezepts im Firestore
fun saveRecipe(recipe: PhytoRecipes) {
  phytoRef.add(recipe)
}

// Löschen eines Rezepts im Firestore
fun deleteRecipe(recipeID: String) {
  phytoRef.document(recipeID).delete()
}
// Funktion um Bild in den Firebase Storage hochzuladen
fun uploadImage(uri: Uri) {
  // Erstellen einer Referenz und des Upload Tasks
  val imageRef = storageRef.child("PhytRezepte")
  val uploadTask = imageRef.putFile(uri)
  // Ausführen des UploadTasks
  uploadTask.addOnCompleteListener {
      imageRef.downloadUrl.addOnCompleteListener {
          if (it.isSuccessful) {
              // Wenn Upload erfolgreich, speichern der Bild-Url
              setImage(it.result)
          }
      }
  }
}
// Funktion um Url zu neue hochgeladenem Bild im Firestore upzudaten
private fun setImage(uri: Uri) {
  recipeRef.update("img", uri.toString()).addOnFailureListener {
      Log.w("ERROR", "Error writing document: $it")
  }
}
}