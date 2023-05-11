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

            if (user.isEmpty()) {
                userEditText.error = "Please enter a name"
                userEditText.requestFocus()
                return@setOnClickListener
            }

            if (eHint.isEmpty()) {
                eHintEditText.error = "Please enter an email"
                eHintEditText.requestFocus()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(eHint).matches()) {
                eHintEditText.error = "Please enter a valid email"
                eHintEditText.requestFocus()
                return@setOnClickListener
            }

            if (phHint.isEmpty()) {
                phHintEditText.error = "Please enter a phone number"
                phHintEditText.requestFocus()
                return@setOnClickListener
            }

            if (!android.util.Patterns.PHONE.matcher(phHint).matches()) {
                phHintEditText.error = "Please enter a valid phone number"
                phHintEditText.requestFocus()
                return@setOnClickListener
            }

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
                        "UserUID" to currentUserUid
                    )

                    // Generate a new interest ID and store the interest data under the "interests" table
                    val newInterestKey = interestsRef.push().key.toString()
                    val newInterestRef = interestsRef.child(newInterestKey)
                    newInterestRef.setValue(interestData)

                    // Set the interest ID generated locally as the key for the interest data node
                    interestData["interestId"] = newInterestKey
                    newInterestRef.updateChildren(interestData as Map<String, Any>)

                    // Finish the activity
                    finish()
                }
            }
        }
    }

}
