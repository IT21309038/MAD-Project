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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AdPostReg : AppCompatActivity() {

    private lateinit var enterFname: EditText
    private lateinit var enterLname: EditText
    private lateinit var enterEmail: EditText
    private lateinit var enterPhone: EditText
    private lateinit var enterPassword: EditText
    private lateinit var reEnterPassword: EditText
    private lateinit var submit: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var usersRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_post_reg)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        usersRef = database.getReference("users")
        enterFname = findViewById(R.id.Fname)
        enterLname = findViewById(R.id.Lname)
        enterEmail = findViewById(R.id.Email)
        enterPhone = findViewById(R.id.Phone)
        enterPassword = findViewById(R.id.Pwd)
        reEnterPassword = findViewById(R.id.Repwd)
        submit = findViewById(R.id.buttonADM)




        submit.setOnClickListener {
            var firstN: String = ""
            var lastN: String = ""
            var email: String = ""
            var phone: String = ""
            var password: String = ""
            var repassword: String = ""

            firstN = enterFname.text.toString()
            lastN = enterLname.text.toString()
            email = enterEmail.text.toString()
            phone = enterPhone.text.toString()
            password = enterPassword.text.toString()
            repassword = reEnterPassword.text.toString()

            if (firstN.isEmpty() || lastN.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || repassword.isEmpty()) {
                Toast.makeText(this@AdPostReg, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else if (password != repassword) {
                Toast.makeText(this@AdPostReg, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                // Call Firebase Authentication's createUserWithEmailAndPassword function
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            Toast.makeText(this@AdPostReg, "Registration Successful", Toast.LENGTH_SHORT).show()

                            // Save user information to Firebase Realtime Database
                            val database = FirebaseDatabase.getInstance()
                            val usersRef = database.getReference("users")

                            val userData = HashMap<String, String>()
                            userData["firstName"] = firstN
                            userData["lastName"] = lastN
                            userData["email"] = email
                            userData["phone"] = phone

                            usersRef.child(user!!.uid).setValue(userData)

                            val intent = Intent(this, AdPostLog::class.java)
                            startActivity(intent)
                            finish()

                            // Additional logic, e.g. navigate to next screen
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(this@AdPostReg, "Registration failed. " + task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }

        // Retrieve the button from the layout
        val clearButton: Button = findViewById(R.id.button2)

        clearButton.setOnClickListener {
            // Clear the text in all the EditText fields
            enterFname.setText("")
            enterLname.setText("")
            enterEmail.setText("")
            enterPhone.setText("")
            enterPassword.setText("")
            reEnterPassword.setText("")
        }




    }
}