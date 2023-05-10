package com.example.mad

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*

class payment_portal : AppCompatActivity() {


    private lateinit var database: DatabaseReference
    private lateinit var adValueEditText: EditText
    private lateinit var paymentTypeEditText: EditText
    private lateinit var cardNameEditText: EditText
    private lateinit var cardHolderNameEditText: EditText
    private lateinit var cardNumberEditText: EditText
    private lateinit var expEditText: EditText
    private lateinit var cvcEditText: EditText
    private lateinit var addPaymentButton: Button
    private lateinit var cardTypeEditText: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_portal)

        database = FirebaseDatabase.getInstance().reference.child("add_payment")

        // Get the payment ID passed through the intent
        val paymentId = intent.getStringExtra("paymentId")

        // Get references to the UI elements
        adValueEditText = findViewById(R.id.editTextNumber)
        cardTypeEditText = findViewById(R.id.editTextTextPersonName5)
        cardNameEditText = findViewById(R.id.editTextTextPersonName3)
        adValueEditText = findViewById(R.id.editTextNumber)
        paymentTypeEditText = findViewById(R.id.editTextTextPersonName5)
        cardNameEditText = findViewById(R.id.editTextTextPersonName3)
        cardHolderNameEditText = findViewById(R.id.editTextTextPersonName4)
        cardNumberEditText = findViewById(R.id.editTextNumber2)
        expEditText = findViewById(R.id.editTextNumber3)
        cvcEditText = findViewById(R.id.editTextNumber4)
        addPaymentButton = findViewById(R.id.button7)


        // Retrieve the payment data from the database
        FirebaseDatabase.getInstance().getReference("payments").child(paymentId!!)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Get the payment object from the snapshot
                    val payment = snapshot.getValue(payments.Payment::class.java)

                    // Set the values of the UI elements
                    payment?.let {
                        adValueEditText.setText(it.adValue)
                        cardTypeEditText.setText(it.cardType)
                        cardNameEditText.setText(it.cardName)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the error
                    Toast.makeText(
                        this@payment_portal,
                        "Failed to retrieve payment data: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        addPaymentButton.setOnClickListener {
            val adValue = adValueEditText.text.toString()
            val paymentType = paymentTypeEditText.text.toString()
            val cardName = cardNameEditText.text.toString()
            val cardHolderName = cardHolderNameEditText.text.toString()
            val cardNumber = cardNumberEditText.text.toString()
            val exp = expEditText.text.toString()
            val cvc = cvcEditText.text.toString()

            if (adValue.isEmpty()) {
                adValueEditText.error = "Ad value is required"
                adValueEditText.requestFocus()
                return@setOnClickListener
            }

            if (paymentType.isEmpty()) {
                paymentTypeEditText.error = "Payment type is required"
                paymentTypeEditText.requestFocus()
                return@setOnClickListener
            }

            if (cardName.isEmpty()) {
                cardNameEditText.error = "Card name is required"
                cardNameEditText.requestFocus()
                return@setOnClickListener
            }

            if (cardHolderName.isEmpty()) {
                cardHolderNameEditText.error = "Card holder name is required"
                cardHolderNameEditText.requestFocus()
                return@setOnClickListener
            }

            if (cardNumber.isEmpty()) {
                cardNumberEditText.error = "Card number is required"
                cardNumberEditText.requestFocus()
                return@setOnClickListener
            }

            if (exp.isEmpty()) {
                expEditText.error = "Expiration date is required"
                expEditText.requestFocus()
                return@setOnClickListener
            }

            if (cvc.isEmpty()) {
                cvcEditText.error = "CVC is required"
                cvcEditText.requestFocus()
                return@setOnClickListener
            }

            val payment =
                Payments(adValue, paymentType, cardName, cardHolderName, cardNumber, exp, cvc)
            savePaymentToDatabase(payment)

            // Create an intent to start the new activity
            val intent = Intent(this@payment_portal, bill_summary::class.java)

            // Pass the selected values as extras to the intent
            intent.putExtra("adValue", adValue)
            intent.putExtra("paymentType", paymentType)
            intent.putExtra("cardName", cardName)
            intent.putExtra("cardHolderName", cardHolderName)
            // Pass the paymentId
            intent.putExtra("paymentId", paymentId)
            startActivity(intent)
            clearEditTexts()

        }
    }

    private fun savePaymentToDatabase(payment: Payments) {
        val paymentRef = database.push()
        paymentRef.setValue(payment.toMap())
            .addOnSuccessListener {
                Toast.makeText(this, "Payment added successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add payment: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun clearEditTexts() {
        adValueEditText.text.clear()
        paymentTypeEditText.text.clear()
        cardNameEditText.text.clear()
        cardHolderNameEditText.text.clear()
        cardNumberEditText.text.clear()
        expEditText.text.clear()
        cvcEditText.text.clear()
    }

    data class Payments(
        val adValue: String? = null,
        val paymentType: String? = null,
        val cardName: String? = null,
        val cardHolderName: String? = null,
        val cardNumber: String? = null,
        val exp: String? = null,
        val cvc: String? = null
    ) {
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "ad_value" to adValue,
                "payment_type" to paymentType,
                "card_name" to cardName,
                "card_holder_name" to cardHolderName,
                "card_number" to cardNumber,
                "exp" to exp,
                "cvc" to cvc
            )
        }
    }
}
