package com.itnovikov.chatbasedonacitivities.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val error = MutableLiveData<String>()
    private val user = MutableLiveData<FirebaseUser>()

    init {
        auth.addAuthStateListener {
            if (it.currentUser != null) {
                user.postValue(it.currentUser)
            }
        }
    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener() { task ->
                if (!task.isSuccessful) {
                    error.postValue("Login failed")
                }
            }
    }

    fun getError(): LiveData<String> {
        return error
    }

    fun getUser(): LiveData<FirebaseUser> {
        return user
    }
}