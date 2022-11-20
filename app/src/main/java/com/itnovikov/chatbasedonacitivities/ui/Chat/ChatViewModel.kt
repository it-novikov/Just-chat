package com.itnovikov.chatbasedonacitivities.ui.Chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.itnovikov.chatbasedonacitivities.data.Message
import com.itnovikov.chatbasedonacitivities.data.User

class ChatViewModel(application: Application, private val currentUserId: String, incomingUserId: String)
    : AndroidViewModel(application) {

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val usersReference = firebaseDatabase.getReference("Users")
    private val messagesReference = firebaseDatabase.getReference("Messages")

    private val messageList = MutableLiveData<List<Message>>()
    private val incomingUser = MutableLiveData<User>()
    private val isSendMessage = MutableLiveData<Boolean>()
    private val error = MutableLiveData<String>()

    init {
        usersReference.child(incomingUserId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java) ?: return
                incomingUser.postValue(user)
            }

            override fun onCancelled(errorMessage: DatabaseError) {
                error.postValue(errorMessage.message)
            }
        })
        messagesReference
            .child(currentUserId)
            .child(incomingUserId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messages = arrayListOf<Message>()
                    for (dataSnapshot in snapshot.children) {
                        val message = dataSnapshot.getValue(Message::class.java) ?: return
                        messages.add(message)
                    }
                    messageList.postValue(messages)
                }

                override fun onCancelled(errorMessage: DatabaseError) {
                    error.postValue(errorMessage.message)
                }
            })
    }

    fun setNetworkStatus(isOnline: Boolean) {
        usersReference.child(currentUserId).child("online").setValue(isOnline)
    }

    fun getMessageList(): LiveData<List<Message>> {
        return messageList
    }

    fun getIncomingUser(): LiveData<User> {
        return incomingUser
    }

    fun getIsSendMessage(): LiveData<Boolean> {
        return isSendMessage
    }

    fun getError(): LiveData<String> {
        return error
    }

    fun sendMessage(message: Message) {
        messagesReference
            .child(message.senderId)
            .child(message.receiverId)
            .push()
            .setValue(message)
            .addOnSuccessListener {
                messagesReference
                    .child(message.receiverId)
                    .child(message.senderId)
                    .push()
                    .setValue(message)
                    .addOnSuccessListener { isSendMessage.postValue(true) }
                    .addOnFailureListener {
                        error.postValue(it.message)
                        isSendMessage.postValue(false)
                    }
            }
            .addOnFailureListener {
                error.postValue(it.message)
                isSendMessage.postValue(false)
            }
    }
}