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
import com.example.maternalmortality.*
import com.example.maternalmortality.models.DoctorPatientDetails
import com.example.maternalmortality.models.PatientDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class ViewSearchedPatientAdapter(var context: Context, var detailsList: MutableList<PatientDetails>):
    RecyclerView.Adapter<ViewSearchedPatientAdapter.DetailsViewHolder>() {

    class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameText: TextView = itemView.findViewById(R.id.nameOfTask)
        var villageText: TextView = itemView.findViewById(R.id.villageOfTask)
        var viewMore: TextView = itemView.findViewById(R.id.viewMore)
        var editButton: ImageView = itemView.findViewById(R.id.editButton)
        var deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        var viewDoctor: TextView = itemView.findViewById(R.id.viewDoctor)
        var viewAllWeek: TextView = itemView.findViewById(R.id.viewAllWeek)

    }

    override fun onBindViewHolder(
        holder: ViewSearchedPatientAdapter.DetailsViewHolder,
        position: Int
    ) {
        val details = detailsList[position]
        holder.nameText.text = details.name
        holder.villageText.text = details.village
        holder.viewMore.text = "View More"

        holder.viewDoctor.setOnClickListener {
            var list: MutableList<DoctorPatientDetails> = mutableListOf()
            val firestore = FirebaseFirestore.getInstance().collection("DoctorPatientDetails")
            firestore.whereEqualTo("name", details.name).whereEqualTo("village", details.village)
                .whereEqualTo(
                    "anm_supervisior_email",
                    FirebaseAuth.getInstance().currentUser?.email
                )
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        list.add(document.toObject(DoctorPatientDetails::class.java))
                    }

                    if (list.isEmpty()) {
                        Toast.makeText(context, "Not Taken Yet", Toast.LENGTH_LONG).show()
                        return@addOnSuccessListener
                    }

                    val intent = Intent(context, ViewDoctorRecordActivity::class.java)
                    intent.putExtra("id", details)
                    context.startActivity(intent)

                }
                .addOnFailureListener {
                    print("Error")
                }

        }

        holder.viewAllWeek.setOnClickListener {

        }



        holder.editButton.setOnClickListener {
            val intent = Intent(context, CollectDataActivity::class.java)
            intent.putExtra("previous details", details)
            intent.putExtra("Id", details.phone)
            context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            var firestore = FirebaseFirestore.getInstance().collection("PatientDetails")

            AlertDialog.Builder(context)
                .setTitle("Delete item")
                .setMessage("Are you sure you want to delete this?")
                .setPositiveButton("Yes") { dialogInterface, i ->
                    firestore.document(details.phone).delete()
                        .addOnSuccessListener {
                            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_LONG)
                                .show()
                            notifyDataSetChanged()
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                        }
                }
                .setNegativeButton("No") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }
                .create()
                .show()

        }

        holder.viewMore.setOnClickListener {
            val intent = Intent(context, ViewMoreDataActivity::class.java)
            intent.putExtra("previous details", details)
            context.startActivity(intent)

        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewSearchedPatientAdapter.DetailsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_assigned_details_item, parent, false)
        return DetailsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return detailsList.size
    }

    fun setList(list: MutableList<PatientDetails>) {
        detailsList = list
        notifyDataSetChanged()
    }
}

