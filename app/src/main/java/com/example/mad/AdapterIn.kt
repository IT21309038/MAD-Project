package com.example.mad

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class AdapterIn(
    private val interestList: ArrayList<Interest>,
    private val databaseRef: DatabaseReference,
    adPostDash: ShowInterest
) : RecyclerView.Adapter<AdapterIn.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.intshow,
            parent, false
        )
        return MyViewHolder(itemView, interestList)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = interestList[position]

        val jobData = currentitem.jobData
        holder.companyName.text = jobData?.get("comName") as? String ?: "N/A"
        holder.companyMail.text = jobData?.get("conMail") as? String ?: "N/A"
        holder.position.text = jobData?.get("position") as? String ?: "N/A"
        holder.Name.text = currentitem.AdViewerName

        holder.deleteJob.setOnClickListener {
            // Remove job from UI
            interestList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, interestList.size)

            // Remove job from Firebase Realtime Database
            val jobRef = databaseRef.child(currentitem.interestId!!)
            jobRef.removeValue(object : DatabaseReference.CompletionListener {
                override fun onComplete(error: DatabaseError?, ref: DatabaseReference) {
                    if (error != null) {
                        Log.e(TAG, "Failed to delete data: ${error.message}")
                    }
                }
            })
        }

        holder.updateJob.setOnClickListener {
            val context = holder.itemView.context
            val jobId = currentitem.interestId
            val intent = Intent(context, UpdateInt::class.java)
            intent.putExtra("interestId", jobId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return interestList.size
    }

    class MyViewHolder(itemView: View, val interestList: ArrayList<Interest>) :
        RecyclerView.ViewHolder(itemView) {
        val companyName: TextView = itemView.findViewById(R.id.SICMn)
        val companyMail: TextView = itemView.findViewById(R.id.SICm)
        val position: TextView = itemView.findViewById(R.id.SIPos)
        val Name: TextView = itemView.findViewById(R.id.SIName)
        val deleteJob: ImageView = itemView.findViewById(R.id.INDe)
        val updateJob: ImageView = itemView.findViewById(R.id.INUp)
    }

    companion object {
        private const val TAG = "AdapterIn"
    }
}
