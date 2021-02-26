package com.example.maternalmortality

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.maternalmortality.models.PatientDetails
import com.google.firebase.firestore.FirebaseFirestore

class DetailsAdapter(var context: Context, var detailsList: MutableList<PatientDetails>):
    RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {

    class DetailsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nameText: TextView = itemView.findViewById(R.id.nameOfTask)
        var villageText: TextView = itemView.findViewById(R.id.villageOfTask)
        var viewMore: TextView = itemView.findViewById(R.id.viewMore)
        var editButton: ImageView = itemView.findViewById(R.id.editButton)
    }
    override fun onBindViewHolder(holder: DetailsAdapter.DetailsViewHolder, position: Int) {
        val details = detailsList[position]
        holder.nameText.text = details.name
        holder.villageText.text = details.village
        holder.viewMore.text = "View More"




        holder.editButton.setOnClickListener {
            val intent = Intent(context,CollectDataActivity::class.java)
            intent.putExtra("previous details",details)
            intent.putExtra("Id",details.phone)
            context.startActivity(intent)
        }
        

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailsAdapter.DetailsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.details_item,parent,false)
        return DetailsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return detailsList.size
    }

    fun setList(list: MutableList<PatientDetails>){
        detailsList = list
        notifyDataSetChanged()
    }
}