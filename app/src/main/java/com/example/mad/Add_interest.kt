package com.example.mad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Add_interest : AppCompatActivity() {
    // Define the Firebase database reference
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_interest)

        // Get a reference to the Firebase database
        database = FirebaseDatabase.getInstance().reference

        // Get references to the UI elements
        val NameEditText = findViewById<EditText>(R.id.user)
        val jobIDEditText = findViewById<EditText>(R.id.j_hint)
        val eMailEditText = findViewById<EditText>(R.id.e_hint)
        val pNumberEditText = findViewById<EditText>(R.id.ph_hint)


        val btn3 = findViewById<Button>(R.id.confirm_btn)

        // Set a click listener on the button
        btn3.setOnClickListener {
            // Get the values of the UI elements
            val comName = NameEditText.text.toString()
            val position = jobIDEditText.text.toString()
            val conMail = eMailEditText.text.toString()
            val jobDes = pNumberEditText.text.toString()


            // Create a Job object
            val job = intrest(comName, position, conMail, jobDes)

            // Save the job object to the database
            database.child("jobs").push().setValue(job.toMap())
                .addOnSuccessListener {
                    // Show a toast message when the data is successfully added
                    Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    // Show an error message when the data could not be added
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }

            // Clear the UI elements
            NameEditText.setText("")
            jobIDEditText.setText("")
            eMailEditText.setText("")
            pNumberEditText.setText("")
        }
    }


}

// Define a data class to represent a job
data class intrest(
    val comName: String = "",
    val position: String = "",
    val conMail: String = "",
    val jobDes: String = "",
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "comName" to comName,
            "position" to position,
            "conMail" to conMail,
            "jobDes" to jobDes,
        )
    }
}