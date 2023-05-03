package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AdPostDash : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_post_dash)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("users")

        val btn7 = findViewById<Button>(R.id.Btn7)
        btn7.setOnClickListener {
            val user = auth.currentUser
            user?.delete()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Delete successful, delete associated data in the database
                    database.child(user.uid).setValue(null)
                        .addOnSuccessListener {
                            // Deletion of associated data successful, sign out user and navigate to login screen
                            auth.signOut()
                            val intent = Intent(this@AdPostDash, AdPostLog::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener {
                            // Deletion of associated data failed, display error message
                            Toast.makeText(this@AdPostDash, "Failed to delete user data. Please try again.", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    // Delete failed, display error message
                    Toast.makeText(this@AdPostDash, "Failed to delete user profile. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val btn8 = findViewById<Button>(R.id.Btn8)
        btn8.setOnClickListener {
            val intent = Intent(this@AdPostDash, ForgotPassword::class.java)
            startActivity(intent)
        }

        val btn5 = findViewById<Button>(R.id.Btn5)
        btn5.setOnClickListener {
            val intent = Intent(this@AdPostDash, AdAd::class.java)
            startActivity(intent)
        }

        val btn6 = findViewById<Button>(R.id.Btn6)
        btn6.setOnClickListener {
            val intent = Intent(this@AdPostDash, AdUpdateDelete::class.java)
            startActivity(intent)
        }
    }
}
