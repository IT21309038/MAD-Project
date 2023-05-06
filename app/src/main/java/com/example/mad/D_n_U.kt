package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class D_n_U : AppCompatActivity() {
    private lateinit var  delAD_btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dn_u)

        delAD_btn = findViewById(R.id.delAD_btn)

        delAD_btn.setOnClickListener {
            val intent = Intent(this, Delete1::class.java)
            startActivity(intent)
        }
    }

}