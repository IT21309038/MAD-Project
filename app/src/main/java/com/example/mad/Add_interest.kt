package com.example.mad

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Add_interest : AppCompatActivity() {
    // Define the Firebase database reference
    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference

    private lateinit var userEditText: EditText
    private lateinit var eHintEditText: EditText
    private lateinit var phHintEditText: EditText
    private lateinit var confirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_interest)

        // Initialize the Firebase database reference
        database = FirebaseDatabase.getInstance()
        ref = database.getReference("interest")

        // Get the job ID from the intent
        val jobId = intent.getStringExtra("id")

        // Initialize the input fields and the "Save" button
        userEditText = findViewById(R.id.user)
        eHintEditText = findViewById(R.id.e_hint)
        phHintEditText = findViewById(R.id.ph_hint)
        confirmButton = findViewById(R.id.confirm_btn)

        // Set a click listener on the "Save" button
        confirmButton.setOnClickListener {
            // Get the values from the input fields
            val user = userEditText.text.toString()
            val eHint = eHintEditText.text.toString()
            val phHint = phHintEditText.text.toString()

            // Create a map of the data to be stored
            val data = hashMapOf(
                "jobId" to jobId,
                "user" to user,
                "eHint" to eHint,
                "phHint" to phHint
            )

            // Generate a hash code for the job ID and store the data in the Firebase database
            val hashCode = jobId.hashCode().toString()
            ref.child(hashCode).setValue(data)

            // Finish the activity
            finish()
        }
    }
}


