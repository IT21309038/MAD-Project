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

class loginadviewer : AppCompatActivity() {

    private lateinit var enterEmail: EditText
    private lateinit var enterPassword: EditText
    private lateinit var loginBtn: Button
    private lateinit var auth: FirebaseAuth

    companion object {
        private const val TAG = "loginadviewer"
    }


    private lateinit var  button2:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_adviewer)

        button2 = findViewById(R.id.button2)

        button2 .setOnClickListener {
            val intent = Intent(this,forgot_password_adviewer::class.java)
            startActivity(intent)
        }



        auth = FirebaseAuth.getInstance()
        enterEmail = findViewById(R.id.editTextTextPersonName)
        enterPassword = findViewById(R.id.editTextTextPassword)
        loginBtn = findViewById(R.id.buttonlog1)

        loginBtn.setOnClickListener {

            val email = enterEmail.text.toString().trim()
            val password = enterPassword.text.toString().trim()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter User Email", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            android.util.Log.d(loginadviewer.TAG, "signInWithEmail:success")
                            val user = auth.currentUser
                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        } else {
                            // If sign in fails, display a message to the user.
                            android.util.Log.w(loginadviewer.TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(this, "Authentication failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        // Handle potential exceptions here
                        android.util.Log.e(loginadviewer.TAG, "signInWithEmailAndPassword failed", exception)
                        Toast.makeText(this, "Authentication failed. ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}