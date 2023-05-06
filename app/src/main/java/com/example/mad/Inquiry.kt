package com.example.mad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.*

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
        val uEmailEditText = findViewById<EditText>(R.id.hint9)
        val uMessageEditText = findViewById<EditText>(R.id.hint10)
        val Btn4 = findViewById<Button>(R.id.btn4)

        // Set a click listener on the button to save data to the database
        Btn3.setOnClickListener {
            // Get the values of the UI elements
            val Email = EmailEditText.text.toString()
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

        // Set up an onclick listener for the update button
        Btn4.setOnClickListener {
            // Get the email and new message data from the UI elements
            val email = uEmailEditText.text.toString()
            val newMessage = uMessageEditText.text.toString()

            // Query the database to find the inquiry object with the matching email
            database.orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // Loop through the matching inquiry objects and update their messages
                        for (inquirySnapshot in dataSnapshot.children) {
                            val inquiry = inquirySnapshot.getValue(InquiryModel::class.java)
                            inquirySnapshot.ref.updateChildren(mapOf("message" to newMessage))
                                .addOnSuccessListener {
                                    // Show a toast message when the data is successfully updated
                                    Toast.makeText(
                                        this@Inquiry,
                                        "Data updated successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .addOnFailureListener {
                                    // Show an error message when the data could not be updated
                                    Toast.makeText(
                                        this@Inquiry,
                                        "Error: ${it.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle any errors that occur during the update process
                    }
                })
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
            "Phone" to Phone,
            "message" to message
            )
        }
}