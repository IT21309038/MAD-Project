package com.example.mad

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Ad_viewer_ui : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var SHJobsArrayList : ArrayList<SHJobs>
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_viewer_ui)

        auth = FirebaseAuth.getInstance()

        val Buton: Button = findViewById(R.id.SHin)
        Buton.setOnClickListener {
            val intent = Intent(this@Ad_viewer_ui, ShowInterest::class.java)
            startActivity(intent)
        }

        val Buton1: Button = findViewById(R.id.SHin2)
        Buton1.setOnClickListener {
            val intent = Intent(this@Ad_viewer_ui, update_adviewer::class.java)
            startActivity(intent)
        }

        val Buton2: Button = findViewById(R.id.SHin3)
        Buton2.setOnClickListener {
            val intent = Intent(this@Ad_viewer_ui, Inquiry::class.java)
            startActivity(intent)
        }

        val btn7 = findViewById<Button>(R.id.del_btn)
        btn7.setOnClickListener {
            val user = auth.currentUser
            user?.delete()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Delete successful, delete associated data in the database
                    dbref.child(user.uid).removeValue().addOnSuccessListener {
                        // Deletion of associated data successful, delete interests of the user in interests table
                        val interestsRef = FirebaseDatabase.getInstance().getReference("interests")
                        interestsRef.child(user.uid).removeValue().addOnSuccessListener {
                            // Deletion of interests successful, sign out user and navigate to login screen
                            auth.signOut()
                            val intent = Intent(this@Ad_viewer_ui, CommonReg::class.java)
                            startActivity(intent)
                            finish()
                        }.addOnFailureListener {
                            // Deletion of interests failed, display error message
                            Toast.makeText(
                                this@Ad_viewer_ui,
                                "Failed to delete user interests. Please try again.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }.addOnFailureListener {
                        // Deletion of associated data failed, display error message
                        Toast.makeText(
                            this@Ad_viewer_ui,
                            "Failed to delete user data. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // Delete failed, display error message
                    Toast.makeText(
                        this@Ad_viewer_ui,
                        "Failed to delete user profile. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
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
