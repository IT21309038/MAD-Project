package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class CommonLog : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_log)

        val img7: ImageView = findViewById(R.id.Img7)
        img7.setOnClickListener {
            val intent = Intent(this@CommonLog, AdPostLog::class.java)
            startActivity(intent)
        }

    }
}