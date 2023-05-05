package com.example.mad


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val userList : ArrayList<User>) : RecyclerView.Adapter<Adapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.jvcard,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.companyMail.text = currentitem.comName
        holder.companyMail.text = currentitem.conMail
        holder.position.text = currentitem.position
        holder.jobDescription.text = currentitem.jobDes


    }

    override fun getItemCount(): Int {

        return userList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val companyName: TextView = itemView.findViewById(R.id.AVCMn)
        val companyMail: TextView = itemView.findViewById(R.id.AVCNm)
        val position: TextView = itemView.findViewById(R.id.AVPOs)
        val jobDescription: TextView = itemView.findViewById(R.id.AVDEs)

    }

}