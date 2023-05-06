package com.example.mad

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class AddViewerReg : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var reEnterPasswordEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_viewer_reg)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        nameEditText = findViewById(R.id.hint1)
        emailEditText = findViewById(R.id.hint2)
        phoneEditText = findViewById(R.id.hint3)
        passwordEditText = findViewById(R.id.hint4)
        reEnterPasswordEditText = findViewById(R.id.hint5)
        registerButton = findViewById(R.id.btn2)

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val password = passwordEditText.text.toString()
            val reEnterPassword = reEnterPasswordEditText.text.toString()

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, "Please enter your full name", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(phone)) {
                Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(reEnterPassword)) {
                Toast.makeText(this, "Please re-enter your password", Toast.LENGTH_SHORT).show()
            } else if (password != reEnterPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            if (user != null) {
                                val userId = user.uid
                                val newUser = User(name, email, phone)
                                database.getReference("viewers").child(userId).setValue(newUser)
                                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, loginadviewer::class.java))
                                finish()
                            } else {
                                Toast.makeText(this, "Registration failed: user is null", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            if (task.exception is FirebaseAuthUserCollisionException) {
                                Toast.makeText(this, "Registration failed: email already exists", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }
        }
    }
}

data class User(val name: String, val email: String, val phone: String)
