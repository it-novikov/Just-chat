package com.itnovikov.chatbasedonacitivities.ui.ChatList

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itnovikov.chatbasedonacitivities.R

class ChatListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val textViewName: TextView = itemView.findViewById(R.id.textViewName)
    val viewNetworkStatus: View = itemView.findViewById(R.id.viewNetworkStatus)
}