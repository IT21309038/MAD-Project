package com.example.mad


import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class AdPostLog : AppCompatActivity() {

    private lateinit var enterUsermail: EditText
    private lateinit var enterPassword: EditText
    private lateinit var Log: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_post_log)


        auth = FirebaseAuth.getInstance()
        enterUsermail = findViewById(R.id.Uname1)
        enterPassword = findViewById(R.id.Pwd1)
        Log = findViewById(R.id.Btn1)

        Log.setOnClickListener{

            var email: String = ""
            var password: String = ""

            email = enterUsermail.text.toString()
            password = enterPassword.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this@AdPostLog, "Enter User Email", Toast.LENGTH_SHORT).show()

            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(this@AdPostLog, "Enter Password", Toast.LENGTH_SHORT).show()
            }else{

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            android.util.Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser
                            Toast.makeText(this@AdPostLog, "Login successful", Toast.LENGTH_SHORT).show()

                            // Navigate to the AdPostLog activity
                            val intent = Intent(this@AdPostLog, AdPostDash::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            android.util.Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(this@AdPostLog, "Authentication failed. " + task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }

            }


        }
        val clearButton: Button = findViewById(R.id.Btn2)

        clearButton.setOnClickListener {
            // Clear the text in all the EditText fields
            enterUsermail.setText("")
            enterPassword.setText("")

        }
    }
}
