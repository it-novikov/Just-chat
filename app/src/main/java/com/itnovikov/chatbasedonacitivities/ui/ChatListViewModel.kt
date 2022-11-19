package com.itnovikov.chatbasedonacitivities.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.itnovikov.chatbasedonacitivities.data.User

class ChatListViewModel(application: Application) : AndroidViewModel(application) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference = firebaseDatabase.getReference("Chats")

    private val onCancelledError = MutableLiveData<String>()
    private val authorizedUser = MutableLiveData<FirebaseUser>()
    private val users = MutableLiveData<List<User>>()

    init {
        auth.addAuthStateListener {
            authorizedUser.postValue(auth.currentUser)
        }
        getAllUsers()
    }

    fun signOut() {
        auth.signOut()
    }

    fun getAuthorizedUser(): LiveData<FirebaseUser> {
        return authorizedUser
    }

    fun getError(): LiveData<String> {
        return onCancelledError
    }

    fun getUsers(): LiveData<List<User>> {
        return users
    }

    private fun getAllUsers() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val usersFromDatabase = ArrayList<User>()
                for (dataSnapshot in snapshot.children) {
                    val user = dataSnapshot.getValue(User::class.java) ?: return
                    usersFromDatabase.add(user)
                }
                users.postValue(usersFromDatabase)
            }

            override fun onCancelled(error: DatabaseError) {
                onCancelledError.postValue(error.message)
            }
        })
    }
}