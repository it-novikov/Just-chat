package com.itnovikov.chatbasedonacitivities.ui

import androidx.recyclerview.widget.DiffUtil
import com.itnovikov.chatbasedonacitivities.data.User

class ChatListDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}