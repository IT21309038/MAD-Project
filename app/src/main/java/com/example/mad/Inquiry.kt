package com.example.mad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Inquiry : AppCompatActivity() {

    // Define the Firebase database reference
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inquiry)

        // Initialize the Firebase database reference
        database = FirebaseDatabase.getInstance().getReference("inquiry")

        // Get references to the UI elements
        val EmailEditText = findViewById<EditText>(R.id.hint6)
        val PhoneEditText = findViewById<EditText>(R.id.hint7)
        val MessageEditText = findViewById<EditText>(R.id.hint8)
        val Btn3 = findViewById<Button>(R.id.btn3)

        // Set a click listener on the button
        Btn3.setOnClickListener {
            // Get the values of the UI elements
            val Email =  EmailEditText.text.toString()
            val Phone = PhoneEditText.text.toString()
            val Message = MessageEditText.text.toString()

            // Create an Inquiry object
            val inquiry = InquiryModel(Email, Phone, Message)

            // Save the inquiry object to the database
            val inquiryId = database.push().key
            database.child(inquiryId!!).setValue(inquiry)
                .addOnSuccessListener {
                    // Show a toast message when the data is successfully added
                    Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    // Show an error message when the data could not be added
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }

            // Clear the UI elements
            EmailEditText.setText("")
            PhoneEditText.setText("")
            MessageEditText.setText("")
        }
    }
}

data class InquiryModel(
    val email: String = "",
    val Phone: String = "",
    val message: String = ""
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "email" to email,
            "position" to Phone,
            "message" to message
            )
        }
}