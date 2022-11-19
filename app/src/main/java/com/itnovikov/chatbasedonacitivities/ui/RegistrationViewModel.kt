package com.itnovikov.chatbasedonacitivities.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.itnovikov.chatbasedonacitivities.data.User

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference = firebaseDatabase.getReference("Users")

    private val error = MutableLiveData<String>()
    private val authorizedUser = MutableLiveData<FirebaseUser>()

    fun createAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val firebaseUser = it.user ?: return@addOnSuccessListener
                val user = User(firebaseUser.uid, "$firstName $lastName", false)
                databaseReference.child(user.id).setValue(user)
                authorizedUser.postValue(it.user)
            }
            .addOnFailureListener {
                error.postValue(it.message)
            }
    }

    fun getError(): LiveData<String> {
        return error
    }

    fun getAuthorizedUser(): LiveData<FirebaseUser> {
        return authorizedUser
    }
}