package com.example.mad

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class admin_dashboard : AppCompatActivity() {

    private lateinit var  btnup: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)


        btnup= findViewById(R.id.button9)

        btnup .setOnClickListener {
            val intent = Intent(this,admin_password_update::class.java)
            startActivity(intent)
        }

    }
}