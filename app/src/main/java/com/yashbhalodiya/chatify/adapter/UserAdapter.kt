package com.yashbhalodiya.chatify.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yashbhalodiya.chatify.R
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
    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val textName = itemView.findViewById<TextView>(R.id.username)
    }
}