package com.yashbhalodiya.chatify.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.yashbhalodiya.chatify.R
import com.yashbhalodiya.chatify.model.Message

class MessageAdapter(val context : Context,var messageList: ArrayList<Message>) : RecyclerView.Adapter<ViewHolder>() {

    val ITEM_REVEIVE = 1
    val ITEM_SEND = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 1){
            val view: View = LayoutInflater.from(context).inflate(R.layout.receiver_chat_layout, parent, false)
            return ReceiverViewHolder(view)
        }else{
            val view: View = LayoutInflater.from(context).inflate(R.layout.sender_chat_layout, parent, false)
            return SenderViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SEND
        }else{
            return ITEM_REVEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val currentMessage = messageList[position]
        if (holder.javaClass == SenderViewHolder::class.java){
            val viewHolder = holder as SenderViewHolder
            holder.senderMessage.text = currentMessage.message
        }else{
            val viewHolder = holder as ReceiverViewHolder
            holder.receiverMessage.text = currentMessage.message
        }
    }

    inner class SenderViewHolder(itemView: View) : ViewHolder(itemView) {
        val senderMessage = itemView.findViewById<TextView>(R.id.senderMessage)
    }

    inner class ReceiverViewHolder(itemView: View) : ViewHolder(itemView){
        val receiverMessage = itemView.findViewById<TextView>(R.id.receiverMessage)
    }
}