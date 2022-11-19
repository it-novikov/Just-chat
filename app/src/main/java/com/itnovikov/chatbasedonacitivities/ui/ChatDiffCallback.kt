package com.itnovikov.chatbasedonacitivities.ui

import androidx.recyclerview.widget.DiffUtil
import com.itnovikov.chatbasedonacitivities.data.Message

class ChatDiffCallback : DiffUtil.ItemCallback<Message>() {

    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return (oldItem.senderId == newItem.senderId && oldItem.receiverId == newItem.receiverId)
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }
}