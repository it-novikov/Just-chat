package com.itnovikov.chatbasedonacitivities.ui.ChatList

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
    private var databaseReference = firebaseDatabase.getReference("Users")

    private val onCancelledError = MutableLiveData<String>()
    private val authorizedUser = MutableLiveData<FirebaseUser>()
    private val users = MutableLiveData<List<User>>()

    init {
        auth.addAuthStateListener {
            authorizedUser.postValue(auth.currentUser)
        }
        getAllUsers()
    }

    fun setNetworkStatus(isOnline: Boolean) {
        val firebaseUser = auth.currentUser ?: return
        databaseReference.child(firebaseUser.uid).child("online").setValue(isOnline)
    }

    fun signOut() {
        setNetworkStatus(false)
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
                val currentUser = auth.currentUser ?: return
                val usersFromDatabase = arrayListOf<User>()
                for (dataSnapshot in snapshot.children) {
                    val user = dataSnapshot.getValue(User::class.java) ?: return
                    if (user.id != currentUser.uid) {
                        usersFromDatabase.add(user)
                    }
                }
                users.postValue(usersFromDatabase)
            }

            override fun onCancelled(error: DatabaseError) {
                onCancelledError.postValue(error.message)
            }
        })
    }
}