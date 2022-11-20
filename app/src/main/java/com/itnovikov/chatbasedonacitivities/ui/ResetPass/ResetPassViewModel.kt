package com.itnovikov.chatbasedonacitivities.ui.ResetPass

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class ResetPassViewModel(application: Application) : AndroidViewModel(application) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val error = MutableLiveData<String>()

    fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    error.postValue("Reset password failed")
                }
            }
    }

    fun getError(): LiveData<String> {
        return error
    }
}