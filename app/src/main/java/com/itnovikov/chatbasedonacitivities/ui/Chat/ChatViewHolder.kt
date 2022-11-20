package com.itnovikov.chatbasedonacitivities.ui.Chat

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itnovikov.chatbasedonacitivities.R

class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val textViewMessage: TextView = itemView.findViewById(R.id.textViewMessage)
}