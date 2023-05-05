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

        val Buton: Button = findViewById(R.id.SHin)
        Buton.setOnClickListener {
            val intent = Intent(this@Ad_viewer_ui, CommonReg::class.java)
            startActivity(intent)
        }

        userRecyclerview = findViewById(R.id.AVRC)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        SHJobsArrayList = arrayListOf<SHJobs>()
        getUserData()


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