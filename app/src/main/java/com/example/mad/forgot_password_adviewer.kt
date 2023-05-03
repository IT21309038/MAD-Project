package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class forgot_password_adviewer : AppCompatActivity() {

    private lateinit var  button3: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_adviewer)



        button3= findViewById(R.id.button3)

        button3 .setOnClickListener {
            val intent = Intent(this,payments::class.java)
            startActivity(intent)
        }

    }
}