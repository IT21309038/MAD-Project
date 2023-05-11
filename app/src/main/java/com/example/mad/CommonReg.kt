package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class CommonReg : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_reg)

        val img8: ImageView = findViewById(R.id.Img8)
        img8.setOnClickListener {
            val intent = Intent(this@CommonReg, AdPostReg::class.java)
            startActivity(intent)
        }
        val img9: ImageView = findViewById(R.id.Img9)
        img9.setOnClickListener {
            val intent = Intent(this@CommonReg, AddViewerReg::class.java)
            startActivity(intent)
        }

    }
}