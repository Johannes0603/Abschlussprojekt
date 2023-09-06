package com.example.abschlussprojekt

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import androidx.lifecycle.ViewModel

class AuthViewModel: ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()

    private var _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)

    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser
    private var _registerFailure = MutableLiveData(false)
    val registerFailure: LiveData<Boolean>
        get() = _registerFailure

    fun signUp(email: String, pass: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    firebaseAuth.currentUser?.sendEmailVerification()
                    logout()
                } else {
                    Log.e("REGISTER", "${authResult.exception}")
                    _registerFailure.value = true
                }
            }
    }

    fun login(email: String, pass: String) {
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { loginResult ->
            if (loginResult.isSuccessful) {
                if (firebaseAuth.currentUser!!.isEmailVerified) {
                    _currentUser.value = firebaseAuth.currentUser
                } else {
                    Log.e("LOGIN", "Email not verified")
                }
            } else {
                Log.e("LOGIN", "${loginResult.exception}")
            }
        }
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