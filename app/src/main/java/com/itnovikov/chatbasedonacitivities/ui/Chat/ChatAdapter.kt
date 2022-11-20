package com.itnovikov.chatbasedonacitivities.ui.Chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.itnovikov.chatbasedonacitivities.R
import com.itnovikov.chatbasedonacitivities.data.Message

class ChatAdapter(private val currentUserId: String)
    : ListAdapter<Message, ChatViewHolder>(ChatDiffCallback()) {

    private val VIEW_TYPE_MY_MESSAGE = 1
    private val VIEW_TYPE_INCOMING_MESSAGE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val layoutResId = if (viewType == VIEW_TYPE_MY_MESSAGE) {
            R.layout.my_message_item
        } else {
            R.layout.incoming_message_item
        }
        val view = LayoutInflater.from(parent.context).inflate(
            layoutResId,
            parent,
            false
        )
        return ChatViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return if (message.senderId == currentUserId) {
            VIEW_TYPE_MY_MESSAGE
        } else {
            VIEW_TYPE_INCOMING_MESSAGE
        }
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = getItem(position)
        holder.textViewMessage.text = message.text.trim()
    }
}