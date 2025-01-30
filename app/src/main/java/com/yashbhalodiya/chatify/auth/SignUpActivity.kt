package com.yashbhalodiya.chatify.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yashbhalodiya.chatify.R
import com.yashbhalodiya.chatify.model.User

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var DBRef : DatabaseReference
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var signUpBtn: Button
    private lateinit var logInBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        DBRef = FirebaseDatabase.getInstance().reference
        name = findViewById(R.id.name_field)
        email = findViewById(R.id.email_field)
        password = findViewById(R.id.password_field)
        signUpBtn = findViewById(R.id.signUpBtn)
        logInBtn = findViewById(R.id.logInBtn)

        signUpBtn.setOnClickListener {
            val nameText = name.text.toString().trim()
            val emailText = email.text.toString().trim()
            val passwordText = password.text.toString().trim()

            if (emailText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            signUpUser(nameText, emailText, passwordText)
        }

        logInBtn.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUpUser(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                addUserToDatabase(name, email, auth.currentUser?.uid!!)
                val intent = Intent(this, LogInActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        DBRef.child("users").child(uid).setValue(User(name, email, uid))
    }
}
