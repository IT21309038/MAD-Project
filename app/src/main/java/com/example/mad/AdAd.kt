package com.example.mad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AdAd : AppCompatActivity() {
    // Define the Firebase database reference
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_ad)

        // Get a reference to the Firebase database
        database = FirebaseDatabase.getInstance().reference

        // Get references to the UI elements
        val comNameEditText = findViewById<EditText>(R.id.ComName)
        val positionEditText = findViewById<EditText>(R.id.Position)
        val conMailEditText = findViewById<EditText>(R.id.ConMail)
        val jobDesEditText = findViewById<EditText>(R.id.JobDes)
        val m1RadioButton = findViewById<RadioButton>(R.id.M1)
        val m2RadioButton = findViewById<RadioButton>(R.id.M2)
        val m3RadioButton = findViewById<RadioButton>(R.id.M3)
        val durationRadioGroup = findViewById<RadioGroup>(R.id.Due)
        val btn3 = findViewById<Button>(R.id.Btn3)
        val btn4 = findViewById<Button>(R.id.Btn4)



        // Set a click listener on the button
        btn3.setOnClickListener {
            // Get the values of the UI elements
            val comName = comNameEditText.text.toString()
            val position = positionEditText.text.toString()
            val conMail = conMailEditText.text.toString()
            val jobDes = jobDesEditText.text.toString()
            val duration = getDurationString(durationRadioGroup.checkedRadioButtonId)

            // Create a Job object
            val job = Job(comName, position, conMail, jobDes, duration)

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
            comNameEditText.setText("")
            positionEditText.setText("")
            conMailEditText.setText("")
            jobDesEditText.setText("")
            durationRadioGroup.clearCheck()
        }

        btn4.setOnClickListener {
            // Get references to the UI elements
            val comNameEditText = findViewById<EditText>(R.id.ComName)
            val positionEditText = findViewById<EditText>(R.id.Position)
            val conMailEditText = findViewById<EditText>(R.id.ConMail)
            val jobDesEditText = findViewById<EditText>(R.id.JobDes)
            val m1RadioButton = findViewById<RadioButton>(R.id.M1)
            val m2RadioButton = findViewById<RadioButton>(R.id.M2)
            val m3RadioButton = findViewById<RadioButton>(R.id.M3)
            val durationRadioGroup = findViewById<RadioGroup>(R.id.Due)

            // Clear the text in all EditText views
            comNameEditText.text.clear()
            positionEditText.text.clear()
            conMailEditText.text.clear()
            jobDesEditText.text.clear()

            // Clear the checked radio button in the RadioGroup
            durationRadioGroup.clearCheck()
        }
    }





    private fun getDurationString(checkedRadioButtonId: Int): String {
        return when (checkedRadioButtonId) {
            R.id.M1 -> "1 month"
            R.id.M2 -> "2 months"
            R.id.M3 -> "3 months"
            else -> ""



        }
    }
}

// Define a data class to represent a job
data class Job(
    val comName: String = "",
    val position: String = "",
    val conMail: String = "",
    val jobDes: String = "",
    val duration: String = ""
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "comName" to comName,
            "position" to position,
            "conMail" to conMail,
            "jobDes" to jobDes,
            "duration" to duration
        )
    }
}

