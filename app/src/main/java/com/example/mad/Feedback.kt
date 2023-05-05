package com.example.mad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Feedback(aInterface: String,find:String) : AppCompatActivity() {
    // Define the Firebase database reference
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        // Get a reference to the Firebase database
        database = FirebaseDatabase.getInstance().reference

        // Get references to the UI elements
        val r1RadioButton = findViewById<RadioButton>(R.id.radioButton1)
        val r2RadioButton = findViewById<RadioButton>(R.id.radioButton2)
        val r3RadioButton = findViewById<RadioButton>(R.id.radioButton3)
        val r4RadioButton = findViewById<RadioButton>(R.id.radioButton4)
        val r5RadioButton = findViewById<RadioButton>(R.id.radioButton5)
        val r6RadioButton = findViewById<RadioButton>(R.id.radioButton6)
        val r7RadioButton = findViewById<RadioButton>(R.id.radioButton7)
        val findRadioGroup = findViewById<RadioGroup>(R.id.rdgrp2)
        val aInterfaceRadioGroup = findViewById<RadioGroup>(R.id.rdgrp1)
        val Btn1 = findViewById<Button>(R.id.btn1)

        // Set a click listener on the button
        Btn1.setOnClickListener {
            // Get the values of the UI elements
            val aInterface = getaInterfaceString(aInterfaceRadioGroup.checkedRadioButtonId)
            val find = getFindString(findRadioGroup.checkedRadioButtonId)

            // Create Feedback objects
            val feedback = FFFeedback(aInterface)
            val ffeedback = FFeedback(find)

            // Save the Feedback objects to the database
            database.child("feedback").push().setValue(feedback.toMap())
                .addOnSuccessListener {
                    // Show a toast message when the data is successfully added
                    Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    // Show an error message when the data could not be added
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }

            database.child("ffeedback").push().setValue(ffeedback.toMap())
                .addOnSuccessListener {
                    // Show a toast message when the data is successfully added
                    Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    // Show an error message when the data could not be added
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }

            // Clear the UI elements
            aInterfaceRadioGroup.clearCheck()
            findRadioGroup.clearCheck()
        }
    }

    private fun toMap(): Any? {
        return (true)
    }

    private fun getFindString(checkedRadioButtonId: Int): String {
        return when (checkedRadioButtonId) {
            R.id.radioButton4 -> "Instagram"
            R.id.radioButton5 -> "Facebook"
            R.id.radioButton6 -> "Google"
            R.id.radioButton7 -> "Other"
            else -> ""
        }
    }

    private fun getaInterfaceString(checkedRadioButtonId: Int): String {
        return when (checkedRadioButtonId) {
            R.id.radioButton1 -> "Good"
            R.id.radioButton2 -> "Poor"
            R.id.radioButton3 -> "Average"
            else -> ""
        }
    }
}

// Define data classes to represent feedback
data class FFFeedback(
    val aInterface: String = ""
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "aInterface" to aInterface
        )
    }
}

data class FFeedback(

    val find: String = ""
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(

            "find" to find
        )
    }
}

