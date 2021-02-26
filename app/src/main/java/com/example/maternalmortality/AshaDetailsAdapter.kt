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
import com.example.maternalmortality.models.New_Registration
import com.google.firebase.firestore.FirebaseFirestore




class AshaDetailsAdapter(var context: Context, var detailsList: MutableList<New_Registration>):
        RecyclerView.Adapter<AshaDetailsAdapter.DetailsViewHolder>() {

    class DetailsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nameText: TextView = itemView.findViewById(R.id.nameOfTask)
        var villageText: TextView = itemView.findViewById(R.id.villageOfTask)
        var viewMore: TextView = itemView.findViewById(R.id.viewMore)

    }
    override fun onBindViewHolder(holder: AshaDetailsAdapter.DetailsViewHolder, position: Int) {
        val details = detailsList[position]
        holder.nameText.text = details.name
        holder.villageText.text = details.village
        holder.viewMore.text = "View More"




//        holder.editButton.setOnClickListener {
//            val intent = Intent(context,CollectDataActivity::class.java)
//            intent.putExtra("previous details",details.name)
//            intent.putExtra("Id",details.mobile)
//            context.startActivity(intent)
//        }


    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): AshaDetailsAdapter.DetailsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.asha_details_item,parent,false)
        return DetailsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return detailsList.size
    }

    fun setList(list: MutableList<New_Registration>){
        detailsList = list
        notifyDataSetChanged()
    }
}