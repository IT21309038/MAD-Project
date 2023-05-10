package com.example.mad

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Admin_login : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        mAuth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.name)
        val passwordEditText = findViewById<EditText>(R.id.password_ad)

        val loginButton = findViewById<Button>(R.id.buttonADM)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                // If any field is empty, display a message to the user and return early
                Toast.makeText(
                    baseContext, "Please fill in all fields.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            // Attempt to sign in with user's email and password
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user: FirebaseUser? = mAuth.currentUser
                        val intent = Intent(this, admin_dashboard::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}