package com.example.mad

import ViewInquiry
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
                    inquiryArrayList.clear()
                    for (inquirySnapshot in snapshot.children){
                        val inquiry = inquirySnapshot.getValue(Iqview::class.java)
                        inquiry!!.id = inquirySnapshot.key // Set inquiry id from the Firebase database
                        inquiryArrayList.add(inquiry)
                    }
                    inquiryRecyclerview.adapter = ViewInquiry(inquiryArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Handle database read error
            }
        })
    }

    // Delete inquiry from the UI and the Firebase database
    fun deleteInquiry(inquiry: Iqview) {
        val inquiryId = inquiry.id
        if (inquiryId != null) {
            dbref.child(inquiryId).removeValue()
                .addOnSuccessListener {
                    // Remove inquiry from the UI
                    inquiryArrayList.remove(inquiry)
                    inquiryRecyclerview.adapter?.notifyDataSetChanged()
                }
                .addOnFailureListener {
                    // Handle database write error
                }
        }
    }
}
