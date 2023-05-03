package com.example.mad

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.mad.R.id.radiogroup1
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class payments : AppCompatActivity() {

    // Define the Firebase database reference
    private lateinit var database: DatabaseReference


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payments)

        // Get a reference to the Firebase database
        database = FirebaseDatabase.getInstance().reference

        // Get references to the UI elements
        val nameEditText = findViewById<EditText>(R.id.editTextNumberDecimal)
        val mailEditText = findViewById<EditText>(R.id.editTextTextEmailAddress3)
        val m2RadioButton = findViewById<RadioButton>(R.id.radio_button_1)
        val m3RadioButton = findViewById<RadioButton>(R.id.radio_button_2)
        val dRadioGroup = findViewById<RadioGroup>(R.id.radiogroup1)
        val IRadioGroup = findViewById<RadioGroup>(R.id.radiogroup2)
        val img1ImageView = findViewById<ImageView>(R.id.imageView10)
        val img2ImageView = findViewById<ImageView>(R.id.imageView11)
        val img3ImageView = findViewById<ImageView>(R.id.imageView9)
        val btn3 = findViewById<Button>(R.id.chip)

        // Set click listeners on the ImageViews
        img1ImageView.setOnClickListener {
            val cardName = "American Card"
            // Save the card name to the database
            savePaymentToDatabase(nameEditText.text.toString(), mailEditText.text.toString(), getDurationString(dRadioGroup.checkedRadioButtonId), getDurationString(IRadioGroup.checkedRadioButtonId), cardName)
        }
        img2ImageView.setOnClickListener {
            val cardName = "Master Card"
            // Save the card name to the database
            savePaymentToDatabase(nameEditText.text.toString(), mailEditText.text.toString(), getDurationString(dRadioGroup.checkedRadioButtonId), getDurationString(IRadioGroup.checkedRadioButtonId), cardName)
        }
        img3ImageView.setOnClickListener {
            val cardName = "Visa Card"
            // Save the card name to the database
            savePaymentToDatabase(nameEditText.text.toString(), mailEditText.text.toString(), getDurationString(dRadioGroup.checkedRadioButtonId), getDurationString(IRadioGroup.checkedRadioButtonId), cardName)
        }

        // Set a click listener on the button
        btn3.setOnClickListener {
            // Get the values of the UI elements
            val conNumber = null
            val conMail = mailEditText.text.toString()
            val duration = getDurationString(dRadioGroup.checkedRadioButtonId)
            val duration1 = getDurationString(IRadioGroup.checkedRadioButtonId)

            // Save the payment to the database
            savePaymentToDatabase(nameEditText.text.toString(), conMail, duration, duration1, "")

            // Clear the UI elements
            nameEditText.setText("")
            mailEditText.setText("")
            dRadioGroup.clearCheck()
            IRadioGroup.clearCheck()
        }
    }

    private fun savePaymentToDatabase(conNumber: String?, conMail: String?, duration: String?, duration1: String?, cardName: String) {
        // Create a Payment object
        val payment = Payment(conNumber, conMail, duration, duration1, cardName)

        // Save the Payment object to the database
        database.child("payments").push().setValue(payment.toMap())
            .addOnSuccessListener {
                // Show a toast message when the data is successfully added
                Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                // Show an error message when the data could not be added
                Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
    }

    private fun getDurationString(checkedRadioButtonId: Int): String {
        return when (checkedRadioButtonId) {
            R.id.radio_button_1 -> "credit card"
            R.id.radio_button_2 -> "debit card"
            R.id.imageView10 -> "american card"
            R.id.imageView11 -> "master card"
            R.id.imageView9 -> "visa card"
            else -> ""
        }
    }


// Define a data class to represent a job
data class Payment(
    val conNumber: String? = null,
    val conMail: String? = null,
    val duration: String? = null,
    val duration1: String? =null,
    val cardName: String? =null,
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "adValue" to conNumber,
            "eMail" to conMail,
            "cardType" to duration,
            "selected card" to cardName,

        )

    }
}