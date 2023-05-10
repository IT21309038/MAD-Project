package com.example.mad

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*

class bill_summary : AppCompatActivity() {
    private lateinit var adValueTextView: TextView
    private lateinit var paymentTypeTextView: TextView
    private lateinit var cardNameTextView: TextView
    private lateinit var cardHolderNameTextView: TextView
    private lateinit var   btn03: Button

    private lateinit var database: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_summary)



        btn03= findViewById(R.id.buttonok)

        btn03 .setOnClickListener {
            val intent = Intent(this@bill_summary,AdPostDash::class.java)
            startActivity(intent)
        }
        // Get the data passed through intent extras
        val adValue = intent.getStringExtra("adValue")
        val paymentType = intent.getStringExtra("paymentType")
        val cardName = intent.getStringExtra("cardName")
        val cardHolderName = intent.getStringExtra("cardHolderName")

        // Initialize UI elements
        adValueTextView = findViewById(R.id.textAdValue)
        paymentTypeTextView = findViewById(R.id.textPaymentType)
        cardNameTextView = findViewById(R.id.textCardName)
        cardHolderNameTextView = findViewById(R.id.textCardHolderName)

        // Set the retrieved data to the UI elements
        adValueTextView.text = adValue
        paymentTypeTextView.text = paymentType
        cardNameTextView.text = cardName
        cardHolderNameTextView.text = cardHolderName

        // Get the payment ID passed through the intent
        val paymentId = intent.getStringExtra("paymentId")

        // Retrieve the remaining payment data from the database using paymentId if needed
        database = FirebaseDatabase.getInstance().getReference("add_payment")
        database.child(paymentId!!)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Get the payment object from the snapshot
                    val payment = snapshot.getValue(payment_portal.Payments::class.java)

                    // Set the remaining values to the respective UI elements
                    payment?.let {
                        // Retrieve and set the remaining data
                        // ...
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the error
                    Toast.makeText(
                        this@bill_summary,
                        "Failed to retrieve payment data: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}