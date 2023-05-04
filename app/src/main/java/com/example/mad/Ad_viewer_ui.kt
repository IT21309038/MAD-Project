package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DatabaseReference
import java.security.AccessController

class Ad_viewer_ui : AppCompatActivity() {
    private lateinit var  ad_int_btn: Button
    private lateinit var del_upd: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_viewer_ui)

        ad_int_btn = findViewById(R.id.ad_int_btn)
        del_upd = findViewById(R.id.del_upd)

        ad_int_btn.setOnClickListener {
            val intent = Intent(this, Add_interest::class.java)
            startActivity(intent)
        }
        del_upd.setOnClickListener {
            val intent = Intent(this, D_n_U::class.java)
            startActivity(intent)
        }


    }


}