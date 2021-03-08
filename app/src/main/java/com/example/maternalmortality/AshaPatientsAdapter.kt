package com.example.maternalmortality

import android.app.Activity
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
import com.google.firebase.firestore.FirebaseFirestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.maternalmortality.auth.RegisterFragment
import com.example.maternalmortality.models.*

import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase
import org.jetbrains.anko.doAsync


class AshaPatientsAdapter(var context: Context, var detailsList: MutableList<PatientDetails>):
    RecyclerView.Adapter<AshaPatientsAdapter.DetailsViewHolder>() {

    class DetailsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nameText: TextView = itemView.findViewById(R.id.nameOfTask)
        var villageText: TextView = itemView.findViewById(R.id.villageOfTask)
        var viewMore: TextView = itemView.findViewById(R.id.viewMore)
        var collectDoctorData: Button = itemView.findViewById(R.id.button5)

    }
    override fun onBindViewHolder(holder: AshaPatientsAdapter.DetailsViewHolder, position: Int) {
        var details = detailsList[position]
        holder.nameText.text = "Name: " + details.name
        holder.villageText.text = "Village: " + details.village
        holder.viewMore.text = "View More"


        holder.viewMore.setOnClickListener {
            val intent = Intent(context,ViewMoreDataActivity::class.java)
            intent.putExtra("previous details",details)
            context.startActivity(intent)

        }

        holder.collectDoctorData.setOnClickListener {

                    val intent = Intent(context, DataForDoctor::class.java)
                    intent.putExtra("previous details", details)
                    context.startActivity(intent)
            (context as Activity).finish()

        }

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