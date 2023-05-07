package com.example.mad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase

class UpdateInt : AppCompatActivity() {

    private lateinit var upName: EditText
    private lateinit var upMail: EditText
    private lateinit var upPhone: EditText
    private lateinit var updateBtn: Button

    private var interestId: String? = null // assuming the interest ID is passed as a string extra

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_int)

        // get reference to views
        upName = findViewById(R.id.UPname)
        upMail = findViewById(R.id.UPmail)
        upPhone = findViewById(R.id.UPphone)
        updateBtn = findViewById(R.id.update_btn2)

        // retrieve the interest ID passed from the previous activity
        interestId = intent.getStringExtra("interestId")

        // set a click listener for the update button
        updateBtn.setOnClickListener {
            // retrieve the values entered by the user
            val name = upName.text.toString()
            val email = upMail.text.toString()
            val phone = upPhone.text.toString()

            if (name.isBlank()) {
                upName.error = "Name is required"
                return@setOnClickListener
            }

            if (email.isBlank()) {
                upMail.error = "Email is required"
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                upMail.error = "Invalid email format"
                return@setOnClickListener
            }

            if (phone.isBlank()) {
                upPhone.error = "Phone number is required"
                return@setOnClickListener
            }

            if (!android.util.Patterns.PHONE.matcher(phone).matches()) {
                upPhone.error = "Invalid phone number format"
                return@setOnClickListener
            }

            // update the database with the new values
            val database = FirebaseDatabase.getInstance()
            val interestRef = database.getReference("interests").child(interestId!!)
            interestRef.child("AdViewerName").setValue(name)
            interestRef.child("Email").setValue(email)
            interestRef.child("Phone Number").setValue(phone)

            // finish the activity to go back to the previous one
            finish()
        }
    }
}
