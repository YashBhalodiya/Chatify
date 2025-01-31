package com.yashbhalodiya.chatify.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.yashbhalodiya.chatify.R
import com.yashbhalodiya.chatify.chat.ChatActivity
import com.yashbhalodiya.chatify.model.User

class UserAdapter(val userList : ArrayList<User>, val context : Context) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.textName.text = currentUser.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uid", currentUser.uid)
            context.startActivity(intent)
        }
    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val textName: TextView = itemView.findViewById(R.id.username)
    }
}