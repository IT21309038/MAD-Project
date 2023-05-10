import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mad.Iqview
import com.example.mad.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ViewInquiry(private val inquiryList : ArrayList<Iqview>) : RecyclerView.Adapter<ViewInquiry.MyViewHolder>() {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val inquiriesRef: DatabaseReference = database.getReference("inquiry")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_inquiry_card, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = inquiryList[position]

        holder.email.text = currentitem.email
        holder.message.text = currentitem.message

        holder.deleteJob.setOnClickListener {
            // Remove item from database
            val inquiryId = currentitem.id
            if (inquiryId != null) {
                inquiriesRef.child(inquiryId).removeValue()
                    .addOnSuccessListener {
                        // Remove item from UI
                        inquiryList.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, inquiryList.size)
                    }
                    .addOnFailureListener {
                        // Handle database write error
                    }
            }
        }
    }

    override fun getItemCount(): Int {
        return inquiryList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val email: TextView = itemView.findViewById(R.id.em)
        val message: TextView = itemView.findViewById(R.id.mess)
        val deleteJob: ImageView = itemView.findViewById(R.id.bin)
    }

}
