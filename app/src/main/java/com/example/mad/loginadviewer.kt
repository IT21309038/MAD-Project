package com.example.mad

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser

class loginadviewer : AppCompatActivity() {

    // Declare FirebaseAuth object
    private lateinit var mAuth: FirebaseAuth

    private lateinit var  btnup1: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_adviewer)
        mAuth = FirebaseAuth.getInstance()


        btnup1= findViewById(R.id.button2)

        btnup1 .setOnClickListener {
            val intent = Intent(this,payments::class.java)
            startActivity(intent)
        }

        val emailEditText = findViewById<EditText>(R.id.editTextTextPersonName)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword)
        val loginButton = findViewById<Button>(R.id.buttonlog1)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user: FirebaseUser? = mAuth.currentUser
                        val intent = Intent(this, update_adviewer::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val exception = task.exception
                        if (exception is FirebaseAuthInvalidUserException) {
                            Toast.makeText(
                                baseContext, "User not found. Please check your email address.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                baseContext, "Authentication failed: ${exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        }
    }
}