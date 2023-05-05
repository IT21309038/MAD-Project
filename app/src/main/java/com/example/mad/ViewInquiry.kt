package com.example.mad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference

class ViewInquiry(private val inquiryList : ArrayList<Iqview>) : RecyclerView.Adapter<ViewInquiry.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.activity_inquiry_card,
            parent, false
        )
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = inquiryList[position]

        holder.email.text = currentitem.email
        holder.phone.text = currentitem.phone
        holder.message.text = currentitem.message

        holder.deleteJob.setOnClickListener {
            // Remove job from UI
            inquiryList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, inquiryList.size)


        }
    }

    override fun getItemCount(): Int {

        return inquiryList.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val email: TextView = itemView.findViewById(R.id.em)
        val phone: TextView = itemView.findViewById(R.id.pho)
        val message: TextView = itemView.findViewById(R.id.mess)
        val deleteJob: ImageView = itemView.findViewById(R.id.bin)

    }

}