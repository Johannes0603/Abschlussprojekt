package com.example.abschlussprojekt.ViewModelPackage

import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class AuthViewModel: ViewModel() {
    // Instanzen der verschiedenen Firebase Services
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseStore = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()

    private var _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)

    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser
    private var _registerFailure = MutableLiveData(false)

    // profileRef ist lateinit, da sie vom currentUser abhängt, wird in Funktion setupUserEnv() gesetzt sobald User eingeloggt wird
    lateinit var profileRef: DocumentReference
    val registerFailure: LiveData<Boolean>
        get() = _registerFailure

    // "Cookie-Funktion": Wenn ein User die App startet und bereits eingeloggt ist
    init {
        if (firebaseAuth.currentUser != null) {
            setupUserEnv()
        }
    }

    // Registrierungs-Funktion: Erstellt neuen User, sendet Bestätigungs-Email und erstellt neues leeres Profil für Nutzer
    fun signUp(email: String, pass: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    // Senden der Email-Bestätigung
                    firebaseAuth.currentUser?.sendEmailVerification()
                    // Kurzzeitiges Aufsetzen der User Environment
                    setupUserEnv()
                    // Logout
                    logout()
                } else {
                    Log.e("REGISTER", "${authResult.exception}")
                }
            }
    }

    // Funktion die User einloggt, überprüt, ob Email bereits bestätigt wurde
    fun login(email: String, pass: String) {
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { loginResult ->
            if (loginResult.isSuccessful) {
                if (firebaseAuth.currentUser!!.isEmailVerified) {
                    setupUserEnv()
                } else {
                    // Wenn Email noch nicht bestätigt wird muss User wieder ausgeloggt werden
                    Log.e("UNVERIFIED", "Email address has not been verified")
                    logout()
                }
            } else {
                Log.e("LOGIN", "${loginResult.exception}")
            }
        }
    }

    // Setzt die nötigen Werte sobald User eingeloggt ist
    private fun setupUserEnv() {
        _currentUser.value = firebaseAuth.currentUser
        profileRef = firebaseStore.collection("profile").document(firebaseAuth.currentUser?.uid!!)
    }


    fun sendPasswordRecovery(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
        }
    }

    fun logout() {
        firebaseAuth.signOut()
        _currentUser.value = firebaseAuth.currentUser
    }

    fun resetRegister() {
        _registerFailure.value = false
    }

}