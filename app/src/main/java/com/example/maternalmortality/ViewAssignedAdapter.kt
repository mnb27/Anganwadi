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
import com.example.maternalmortality.models.PatientDetails
import com.google.firebase.firestore.FirebaseFirestore



class ViewAssignedAdapter(var context: Context, var detailsList: MutableList<PatientDetails>):
    RecyclerView.Adapter<ViewAssignedAdapter.DetailsViewHolder>() {

    class DetailsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nameText: TextView = itemView.findViewById(R.id.nameOfTask)
        var villageText: TextView = itemView.findViewById(R.id.villageOfTask)
        var viewMore: TextView = itemView.findViewById(R.id.viewMore)
        var editButton: ImageView = itemView.findViewById(R.id.editButton)
        var deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }
    override fun onBindViewHolder(holder: ViewAssignedAdapter.DetailsViewHolder, position: Int) {
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

        holder.deleteButton.setOnClickListener{
            var firestore = FirebaseFirestore.getInstance().collection("PatientDetails")

            AlertDialog.Builder(context)
                .setTitle("Delete item")
                .setMessage("Are you sure you want to delete this?")
                .setPositiveButton("Yes") { dialogInterface, i ->
                    firestore.document(details.phone).delete()
                        .addOnSuccessListener {
                            Toast.makeText(context,"Successfully Deleted",Toast.LENGTH_LONG).show()
                            notifyDataSetChanged()
                        }
                        .addOnFailureListener{
                            Toast.makeText(context,"Error",Toast.LENGTH_LONG).show()
                        }
                }
                .setNegativeButton("No") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }
                .create()
                .show()

        }

        holder.viewMore.setOnClickListener {
            val intent = Intent(context,ViewMoreDataActivity::class.java)
            intent.putExtra("previous details",details)
            context.startActivity(intent)

        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewAssignedAdapter.DetailsViewHolder {
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