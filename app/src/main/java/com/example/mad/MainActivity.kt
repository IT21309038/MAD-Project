package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val img1: ImageView = findViewById(R.id.Img1)
        img1.setOnClickListener {
            val intent = Intent(this@MainActivity, CommonLog::class.java)
            startActivity(intent)
        }

        val img2: ImageView = findViewById(R.id.Img2)
        img2.setOnClickListener {
            val intent = Intent(this@MainActivity, CommonReg::class.java)
            startActivity(intent)
        }

    }
}