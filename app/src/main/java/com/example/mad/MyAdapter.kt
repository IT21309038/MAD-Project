package com.example.mad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference

class MyAdapter(
    private val jobsList: ArrayList<Jobs>,
    private val databaseRef: DatabaseReference
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.jobcard,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = jobsList[position]

        holder.companyName.text = currentitem.comName
        holder.companyMail.text = currentitem.conMail
        holder.position.text = currentitem.position
        holder.jobDescription.text = currentitem.jobDes

        holder.deleteJob.setOnClickListener {
            // Remove job from UI
            jobsList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, jobsList.size)

            // Remove job from Firebase Realtime Database
            val jobRef = databaseRef.child(currentitem.id!!)
            jobRef.removeValue()
        }
    }

    override fun getItemCount(): Int {
        return jobsList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val companyName: TextView = itemView.findViewById(R.id.CMn)
        val companyMail: TextView = itemView.findViewById(R.id.CNm)
        val position: TextView = itemView.findViewById(R.id.POs)
        val jobDescription: TextView = itemView.findViewById(R.id.DEs)
        val deleteJob: ImageView = itemView.findViewById(R.id.CardD)
    }
}
