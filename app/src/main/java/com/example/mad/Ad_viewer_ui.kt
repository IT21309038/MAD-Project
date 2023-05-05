package com.example.mad

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class Ad_viewer_ui : AppCompatActivity() {
    private lateinit var  ad_int_btn: Button
    private lateinit var del_upd: Button
    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var SHJobsArrayList : ArrayList<SHJobs>


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_viewer_ui)

        userRecyclerview = findViewById(R.id.AVRC)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        SHJobsArrayList = arrayListOf<SHJobs>()
        getUserData()

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

    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("jobs")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val SHJobs = userSnapshot.getValue(SHJobs::class.java)
                        SHJobsArrayList.add(SHJobs!!)

                    }

                    userRecyclerview.adapter = Adapter(SHJobsArrayList)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }


}