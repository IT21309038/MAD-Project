package com.example.mad

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.mad.R.id.radiogroup1
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.security.Key


class payments : AppCompatActivity() {

    // Define the Firebase database reference
    private lateinit var database: DatabaseReference

    // Define a variable to store the selected card name
    private var selectedCardName: String? = null

    private lateinit var  btn:Button

    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payments)

        FirebaseApp.initializeApp(this)



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
        val btn3 = findViewById<Button>(R.id.button11)

        // Set click listeners on the ImageViews
        img1ImageView.setOnClickListener {
            selectedCardName = "American Card"
        }
        img2ImageView.setOnClickListener {
            selectedCardName = "Master Card"
        }
        img3ImageView.setOnClickListener {
            selectedCardName = "Visa Card"
        }

        // Set a click listener on the button
        btn3.setOnClickListener {
            // Get the values of the UI elements
            val adValue = nameEditText.text.toString()
            val conMail = mailEditText.text.toString()
            val cardType = getDurationString(dRadioGroup.checkedRadioButtonId)
            val duration1 = getDurationString(IRadioGroup.checkedRadioButtonId)



            val adValueEditText = findViewById<EditText>(R.id.editTextNumberDecimal)

            if (adValueEditText.text.isNullOrEmpty()) {
                adValueEditText.error = "Please enter a value for Ad-value"
                return@setOnClickListener
            }
            val emailEditText = findViewById<EditText>(R.id.editTextTextEmailAddress3)

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.text.toString()).matches()) {
                emailEditText.error = "Please enter a valid email address"
                return@setOnClickListener
            }

            if (selectedCardName.isNullOrEmpty()) {
                Toast.makeText(this, "Please select a card", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cardTypeRadioGroup = findViewById<RadioGroup>(R.id.radiogroup1)

            if (cardTypeRadioGroup.checkedRadioButtonId == -1) {
                Toast.makeText(this, "Please select a card type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save the payment to the database with the selected card name
            savePaymentToDatabase(adValue, conMail, cardType, duration1, selectedCardName)

            // Clear the UI elements
            nameEditText.setText("")
            mailEditText.setText("")
            dRadioGroup.clearCheck()
            IRadioGroup.clearCheck()
        }
    }

    private fun savePaymentToDatabase(
        adValue: String?,
        conMail: String?,
        cardType: String?,
        duration1: String?,
        cardName: String?
    ) {
        // Create a Payment object
        val payment = Payment(adValue, conMail, cardType, duration1, cardName)

        // Save the Payment object to the database
        val paymentRef = database.child("payments").push()
        paymentRef.setValue(payment.toMap())
            .addOnSuccessListener {
                // Show a toast message when the data is successfully added
                Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show()

                // Get the ID of the newly added payment
                val paymentId = paymentRef.key

                if (paymentId != null) {
                    // Create an intent and add the payment ID as an extra
                    val intent = Intent(this@payments, payment_portal::class.java)
                    intent.putExtra("paymentId", paymentId)
                    startActivity(intent)
                } else {
                    // Handle the case when the payment ID is null
                    Toast.makeText(this, "Error: Payment ID is null", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                // Show an error message when the data could not be added
                Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                Log.e("Firebase", "Data insertion failed: ${exception.message}")
            }
    }


    private fun getDurationString(checkedRadioButtonId: Int): String {
        return when (checkedRadioButtonId) {
            R.id.radio_button_1 -> "credit card"
            R.id.radio_button_2 -> "debit card"
            else -> ""
        }
    }

    // Define a data class to represent a payment
    data class Payment(
        val adValue: String? = null,
        val conMail: String? = null,
        val cardType: String? = null,
        val duration1: String? = null,
        val cardName: String? = null

    ) {
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "Ad-value" to adValue,
                "conMail" to conMail,
                "cardType" to cardType,
                "cardName" to cardName,
            )
        }
    }
}