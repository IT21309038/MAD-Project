package com.example.mad

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val userList: ArrayList<SHJobs>) : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.jvcard, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userList[position]

        holder.companyName.text = currentitem.comName
        holder.companyMail.text = currentitem.conMail
        holder.position.text = currentitem.position
        holder.jobDescription.text = currentitem.jobDes

        holder.addInterestButton.setOnClickListener {
            val intent = Intent(context, Add_interest::class.java)
            intent.putExtra("id", currentitem.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val companyName: TextView = itemView.findViewById(R.id.AVCMn)
        val companyMail: TextView = itemView.findViewById(R.id.AVCNm)
        val position: TextView = itemView.findViewById(R.id.AVPOs)
        val jobDescription: TextView = itemView.findViewById(R.id.AVDEs)
        val addInterestButton: ImageView = itemView.findViewById(R.id.AVAdd)
    }
}
