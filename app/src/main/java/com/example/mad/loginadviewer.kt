package com.example.mad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class loginadviewer : AppCompatActivity() {

    // Declare FirebaseAuth object
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginadviewer)

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance()

        // Get references to the email and password EditText fields
        val emailEditText = findViewById<EditText>(R.id.editTextTextPersonName)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword)

        // Get reference to the login button
        val loginButton = findViewById<Button>(R.id.buttonlog1)

        // Set click listener for login button
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Attempt to sign in with user's email and password
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user: FirebaseUser? = mAuth.currentUser
                        val intent = Intent(this, Ad_viewer_ui::class.java)
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
