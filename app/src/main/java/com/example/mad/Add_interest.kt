package com.example.mad

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Add_interest : AppCompatActivity() {

    // Define the Firebase database reference
    private lateinit var database: FirebaseDatabase
    private lateinit var jobsRef: DatabaseReference
    private lateinit var interestsRef: DatabaseReference
    private lateinit var currentUserUid: String

    private lateinit var userEditText: EditText
    private lateinit var eHintEditText: EditText
    private lateinit var phHintEditText: EditText
    private lateinit var confirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_interest)

        // Initialize the Firebase database reference
        database = FirebaseDatabase.getInstance()
        jobsRef = database.getReference("jobs")
        interestsRef = database.getReference("interests")

        // Get the current user's UID
        currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

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

            // Retrieve the job data from the Firebase database
            jobId?.let { nonNullJobId ->
                jobsRef.child(nonNullJobId).get().addOnSuccessListener { jobSnapshot ->
                    val jobData = jobSnapshot.value as HashMap<String, Any>

                    // Create a map of the interest data to be stored
                    val interestData = hashMapOf(
                        "jobData" to jobData,
                        "AdViewerName" to user,
                        "Email" to eHint,
                        "Phone Number" to phHint,
                        "UserUID" to currentUserUid,
                        "interestId" to interestsRef.push().key.toString()
                    )

                    // Generate a new interest ID and store the interest data under the "interests" table
                    val newInterestRef = interestsRef.push()
                    newInterestRef.setValue(interestData)

                    // Finish the activity
                    finish()
                }
            }
        }
    }

}
