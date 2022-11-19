package com.itnovikov.chatbasedonacitivities.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChatViewModelFactory(
    private val application: Application,
    private val currentUserId: String,
    private val incomingUserId: String
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(application, currentUserId, incomingUserId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}