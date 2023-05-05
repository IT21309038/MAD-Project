package com.example.mad

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference

class AdapterIn(
    private val interestList: ArrayList<Interest>,
    private val databaseRef: DatabaseReference,
    adPostDash: AdPostDash
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

        holder.companyName.text = currentitem.comName
        holder.companyMail.text = currentitem.conMail
        holder.position.text = currentitem.position
        holder.Name.text = currentitem.AdViewerName

        holder.deleteJob.setOnClickListener {
            // Remove job from UI
            interestList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, interestList.size)

            // Remove job from Firebase Realtime Database
            val jobRef = databaseRef.child(currentitem.InterestId!!)
            jobRef.removeValue()
        }

        holder.updateJob.setOnClickListener {
            val context = holder.itemView.context
            val jobId = currentitem.InterestId
            val intent = Intent(context, UpdateInt::class.java)
            intent.putExtra("InterestId", jobId)
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return interestList.size
    }



    class MyViewHolder(itemView: View, val interestList: ArrayList<Interest>) : RecyclerView.ViewHolder(itemView) {
        val companyName: TextView = itemView.findViewById(R.id.SICMn)
        val companyMail: TextView = itemView.findViewById(R.id.SICm)
        val position: TextView = itemView.findViewById(R.id.SIPos)
        val Name: TextView = itemView.findViewById(R.id.SIName)
        val deleteJob: ImageView = itemView.findViewById(R.id.INDe)
        val updateJob: ImageView = itemView.findViewById(R.id.INUp)
    }
}
