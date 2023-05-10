package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AdPostDash : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var dbref: DatabaseReference
    private lateinit var userRecyclerview: RecyclerView
    private lateinit var jobsArrayList: ArrayList<Jobs>
    private lateinit var adapter: MyAdapter // Add adapter property

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_post_dash)

        auth = FirebaseAuth.getInstance()
        userRecyclerview = findViewById(R.id.RC1)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        jobsArrayList = arrayListOf<Jobs>()
        adapter = MyAdapter(jobsArrayList, FirebaseDatabase.getInstance().getReference("jobs"), this)
        userRecyclerview.adapter = adapter

        getUserData()


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("users")

        val btn7 = findViewById<Button>(R.id.Btn7)
        btn7.setOnClickListener {
            val user = auth.currentUser
            user?.delete()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Delete successful, delete associated data in the database
                    database.child(user.uid).setValue(null)
                        .addOnSuccessListener {
                            // Deletion of associated data successful, sign out user and navigate to login screen
                            auth.signOut()
                            val intent = Intent(this@AdPostDash, AdPostLog::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener {
                            // Deletion of associated data failed, display error message
                            Toast.makeText(
                                this@AdPostDash,
                                "Failed to delete user data. Please try again.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    // Delete failed, display error message
                    Toast.makeText(
                        this@AdPostDash,
                        "Failed to delete user profile. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        val btn2 = findViewById<Button>(R.id.btn2)
        btn2.setOnClickListener {
            val intent = Intent(this@AdPostDash, ForgotPassword::class.java)
            startActivity(intent)
        }

        val logoutButton: Button = findViewById(R.id.LGout)
        logoutButton.setOnClickListener {
            // Perform logout action here
            // For example, clear user session data or sign the user out of your backend
            // Then navigate to CommonLog activity
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        val btn5 = findViewById<Button>(R.id.Btn5)
        btn5.setOnClickListener {
            val intent = Intent(this@AdPostDash, AdAd::class.java)
            startActivity(intent)
        }


    }


    private fun getUserData() {
        val currentUserUid = auth.currentUser!!.uid
        dbref = FirebaseDatabase.getInstance().getReference("jobs")

        dbref.orderByChild("uid").equalTo(currentUserUid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        jobsArrayList.clear()
                        for (jobSnapshot in snapshot.children) {
                            val jobs = jobSnapshot.getValue(Jobs::class.java)
                            jobsArrayList.add(jobs!!)
                        }
                        adapter.notifyDataSetChanged() // Notify adapter that data set has changed
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@AdPostDash,
                        "Failed to retrieve user data. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}

