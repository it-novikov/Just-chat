package com.itnovikov.chatbasedonacitivities.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.itnovikov.chatbasedonacitivities.R
import com.itnovikov.chatbasedonacitivities.data.User

class ChatListAdapter : ListAdapter<User, ChatListViewHolder>(ChatListDiffCallback()) {

    private var onItemClick: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.chat_item,
            parent,
            false
        )
        return ChatListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        val user = getItem(position)
        holder.textViewName.text = user.name
        if (user.networkStatus) {
            holder.viewNetworkStatus.setBackgroundResource(R.drawable.circle_green)
        } else {
            holder.viewNetworkStatus.setBackgroundResource(R.drawable.circle_red)
        }
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(user)
        }
    }

    fun setOnItemClick(function: ((User) -> Unit)?) {
        onItemClick = function
    }

}