package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AdPostLog : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_post_log)

        val btn8 = findViewById<Button>(R.id.Btn8)
        btn8.setOnClickListener {
            val intent = Intent(this@AdPostLog, ForgotPassword::class.java)
            startActivity(intent)
        }

    }
}