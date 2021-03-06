package com.example.maternalmortality

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
import com.example.maternalmortality.models.PatientDetails
import com.google.firebase.firestore.FirebaseFirestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.maternalmortality.auth.RegisterFragment
import com.example.maternalmortality.models.User
import com.example.maternalmortality.models.AshaUser
import com.example.maternalmortality.models.ANMUser

import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase


class AshaPatientsAdapter(var context: Context, var detailsList: MutableList<PatientDetails>):
    RecyclerView.Adapter<AshaPatientsAdapter.DetailsViewHolder>() {

    class DetailsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nameText: TextView = itemView.findViewById(R.id.nameOfTask)
        var villageText: TextView = itemView.findViewById(R.id.villageOfTask)
        var viewMore: TextView = itemView.findViewById(R.id.viewMore)
        var approve: Button = itemView.findViewById(R.id.button5)

    }
    override fun onBindViewHolder(holder: AshaPatientsAdapter.DetailsViewHolder, position: Int) {
        var details = detailsList[position]
        holder.nameText.text = "Name: " + details.name
        holder.villageText.text = "Village: " + details.village
        holder.viewMore.text = "View More"
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AshaPatientsAdapter.DetailsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.asha_patients_list,parent,false)
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