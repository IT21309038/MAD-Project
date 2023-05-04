package com.example.mad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AdUpdateDelete : AppCompatActivity() {
    private lateinit var databaseRef: DatabaseReference
    private lateinit var jobId: String
    private lateinit var existingJob: Jobs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_update_delete)

        // Get a reference to the Firebase Realtime Database
        databaseRef = FirebaseDatabase.getInstance().getReference("jobs")

        // Get the jobId passed as an extra from the intent
        jobId = intent.getStringExtra("jobId")!!

        // Get references to the EditText views and the update button
        val cNameInput: EditText = findViewById(R.id.UPCname)
        val positionInput: EditText = findViewById(R.id.UPCposition)
        val cMailInput: EditText = findViewById(R.id.UPCmail)
        val descriptionInput: EditText = findViewById(R.id.UPCdes)
        val updateButton: Button = findViewById(R.id.button3)

        // Retrieve the job data from Firebase Realtime Database and pre-fill the EditText views
        databaseRef.child(jobId).get().addOnSuccessListener { snapshot ->
            if (snapshot != null && snapshot.exists()) {
                existingJob = snapshot.getValue(Jobs::class.java)!!
                cNameInput.setText(existingJob.comName)
                positionInput.setText(existingJob.position)
                cMailInput.setText(existingJob.conMail)
                descriptionInput.setText(existingJob.jobDes)
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Error retrieving job data: ${exception.message}", Toast.LENGTH_SHORT).show()
        }

        // Set an onClickListener to the update button to update the job data in Firebase Realtime Database
        updateButton.setOnClickListener {
            val cName = cNameInput.text.toString().trim()
            val position = positionInput.text.toString().trim()
            val cMail = cMailInput.text.toString().trim()
            val description = descriptionInput.text.toString().trim()

            // Validate input fields
            if (cName.isEmpty() || position.isEmpty() || cMail.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a map of the updated fields
            val updatedFields = mapOf(
                "comName" to cName,
                "position" to position,
                "conMail" to cMail,
                "jobDes" to description,
                "uid" to existingJob.uid

            )

            // Update the job data in Firebase Realtime Database
            databaseRef.child(jobId).updateChildren(updatedFields).addOnSuccessListener {
                Toast.makeText(this, "Job updated successfully.", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener { exception ->
                Toast.makeText(this, "Error updating job data: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
