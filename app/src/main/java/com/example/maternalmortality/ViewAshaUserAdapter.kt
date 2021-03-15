package com.example.maternalmortality

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.maternalmortality.models.ANMUser
import com.example.maternalmortality.models.AshaUser
import com.example.maternalmortality.models.PatientDetails
import com.google.firebase.firestore.FirebaseFirestore



class ViewAshaUserAdapter(var context: Context, var detailsList: MutableList<AshaUser>):
    RecyclerView.Adapter<ViewAshaUserAdapter.DetailsViewHolder>() {

    class DetailsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nameText: TextView = itemView.findViewById(R.id.nameOfTask)
        var villageText: TextView = itemView.findViewById(R.id.villageOfTask)
        var viewMore: TextView = itemView.findViewById(R.id.viewMore)

    }
    override fun onBindViewHolder(holder: ViewAshaUserAdapter.DetailsViewHolder, position: Int) {
        val details = detailsList[position]
        holder.nameText.text = details.name
        holder.villageText.text = details.village
        holder.viewMore.text = details.mobile




    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewAshaUserAdapter.DetailsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false)
        return DetailsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return detailsList.size
    }

    fun setList(list: MutableList<AshaUser>){
        detailsList = list
        notifyDataSetChanged()
    }


}