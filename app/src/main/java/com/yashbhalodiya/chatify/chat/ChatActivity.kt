package com.yashbhalodiya.chatify.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.yashbhalodiya.chatify.R
import com.yashbhalodiya.chatify.adapter.MessageAdapter
import com.yashbhalodiya.chatify.model.Message

class ChatActivity : AppCompatActivity() {

    private lateinit var dBRef: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendBtn: ImageButton
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)
        setSupportActionBar(findViewById(R.id.toolbar))
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        val intent = Intent()
        val name = intent.getStringExtra("name")
        val receiverUID = intent.getStringExtra("uid")
        val senderUID = FirebaseAuth.getInstance().currentUser?.uid


        senderRoom = receiverUID + senderUID
        receiverRoom = senderUID + receiverUID

        setContentView(R.layout.activity_chat)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = name ?: "Chat"

        dBRef = FirebaseDatabase.getInstance().reference
        recyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sendBtn = findViewById(R.id.send_btn)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = messageAdapter


        // adding messages to recycler view
        dBRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for(postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyItemInserted(messageList.size - 1)
                }

                override fun onCancelled(error: DatabaseError) {
                    // TODO("Not yet implemented")
                }

            })

        // adding messages to DB
        sendBtn.setOnClickListener {
            val message = messageBox.text.toString()
            val messageObject = Message(message, senderUID)

            dBRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    Log.d("ChatActivity", "Message sent successfully.")
                    dBRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }.addOnFailureListener {
                    Log.e("ChatActivity", "Error sending message: ${it.message}")
                }
            messageBox.setText("")
        }
    }
}