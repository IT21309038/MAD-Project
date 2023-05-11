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

        val img5: ImageView = findViewById(R.id.Img5)
        img5.setOnClickListener {
            val intent = Intent(this@CommonLog, Admin_login::class.java)
            startActivity(intent)
        }

        val img6: ImageView = findViewById(R.id.Img6)
        img6.setOnClickListener {
            val intent = Intent(this@CommonLog, loginadviewer::class.java)
            startActivity(intent)
        }

            val img2: ImageView = findViewById(R.id.Img6)
            img2.setOnClickListener {
                val intent = Intent(this@CommonLog,loginadviewer::class.java)
                startActivity(intent)
            }

        
        }


    }
