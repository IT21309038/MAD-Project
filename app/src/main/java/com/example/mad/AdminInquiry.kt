package com.example.mad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class AdminInquiry : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var inquiryRecyclerview : RecyclerView
    private lateinit var inquiryArrayList : ArrayList<Iqview>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_inquiry)

        inquiryRecyclerview = findViewById(R.id.recyclerView1)
        inquiryRecyclerview.layoutManager = LinearLayoutManager(this)
        inquiryRecyclerview.setHasFixedSize(true)

        inquiryArrayList = arrayListOf<Iqview>()
        getInquiryData()


    }
    private fun getInquiryData() {

        dbref = FirebaseDatabase.getInstance().getReference("inquiry")

        dbref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (inquirySnapshot in snapshot.children){
                        val inquiry = inquirySnapshot.getValue(Iqview::class.java)
                        inquiryArrayList.add(inquiry!!)

                    }
                    inquiryRecyclerview.adapter = ViewInquiry(inquiryArrayList)
                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}


