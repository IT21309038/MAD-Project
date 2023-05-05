package com.example.mad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ShowInterest : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var dbref: DatabaseReference
    private lateinit var userRecyclerview: RecyclerView
    private lateinit var jobsArrayList: ArrayList<Interest>
    private lateinit var adapter: AdapterIn // Add adapter property
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_interest)

        auth = FirebaseAuth.getInstance()
        userRecyclerview = findViewById(R.id.SHInt)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        jobsArrayList = arrayListOf<Interest>()
        adapter = AdapterIn(jobsArrayList, FirebaseDatabase.getInstance().getReference("interests"), this)
        userRecyclerview.adapter = adapter

        getUserData()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("users")


    }

    private fun getUserData() {
        val currentUserUid = auth.currentUser!!.uid
        dbref = FirebaseDatabase.getInstance().getReference("interests")

        dbref.orderByChild("UserUID").equalTo(currentUserUid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        jobsArrayList.clear()
                        for (jobSnapshot in snapshot.children) {
                            val jobs = jobSnapshot.getValue(Interest::class.java)
                            jobsArrayList.add(jobs!!)
                        }
                        adapter.notifyDataSetChanged() // Notify adapter that data set has changed
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@ShowInterest,
                        "Failed to retrieve user data. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}