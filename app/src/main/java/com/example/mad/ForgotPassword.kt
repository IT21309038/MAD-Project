package com.example.mad

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ForgotPassword : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var newPassword: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var updateBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        mAuth = FirebaseAuth.getInstance()
        user = mAuth.currentUser!!

        newPassword = findViewById(R.id.Pwd2)
        confirmPassword = findViewById(R.id.Pwd3)
        updateBtn = findViewById(R.id.Btn9)

        updateBtn.setOnClickListener {
            val password = newPassword.text.toString().trim()
            val confirm = confirmPassword.text.toString().trim()

            if (password.isNotEmpty() && password == confirm) {
                user.updatePassword(password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Password updated successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Start the AdPostDash activity
                        val intent = Intent(this@ForgotPassword, AdPostDash::class.java)
                        startActivity(intent)
                        finish() // Close this activity to prevent the user from going back to it using the back button
                    } else {
                        Toast.makeText(
                            this,
                            "Failed to update password. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    "Passwords do not match. Please try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
