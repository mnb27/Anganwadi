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
import com.example.maternalmortality.models.New_Registration
import com.google.firebase.firestore.FirebaseFirestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.maternalmortality.auth.RegisterFragment
import com.example.maternalmortality.models.PatientDetails
import com.example.maternalmortality.models.User
import com.example.maternalmortality.models.AshaUser
import com.example.maternalmortality.models.ANMUser

import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase


class AshaDetailsAdapter(var context: Context, var detailsList: MutableList<New_Registration>):
        RecyclerView.Adapter<AshaDetailsAdapter.DetailsViewHolder>() {

    class DetailsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nameText: TextView = itemView.findViewById(R.id.nameOfTask)
        var villageText: TextView = itemView.findViewById(R.id.villageOfTask)
        var viewMore: TextView = itemView.findViewById(R.id.viewMore)
        var approve: Button = itemView.findViewById(R.id.button5)

    }
    override fun onBindViewHolder(holder: AshaDetailsAdapter.DetailsViewHolder, position: Int) {
        var details = detailsList[position]
        holder.nameText.text = details.name
        holder.villageText.text = details.village
        holder.viewMore.text = "View More"




        holder.approve.setOnClickListener {
            var Docref = FirebaseFirestore.getInstance().collection("New_Registration").document(details.mobile);
            var ans = "approved";
            Docref.update("status" , ans);
            val auth = FirebaseAuth.getInstance()
            var email = details.email.toString()
            var password = "Test12345"
            var name = details.name
            var mobile = details.mobile
            var village = details.village
            var count:Int = 0

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            if(details.category=="asha")
                            {
                                val user = AshaUser(
                                        auth.currentUser?.uid!!,
                                        name,
                                        email,
                                        village,
                                        mobile,
                                        count
                                )
                                val firestore = FirebaseFirestore.getInstance().collection("AshaUser")
                                firestore.document(mobile).set(user)

                            }
                            else
                            {
                                val user = ANMUser(
                                        auth.currentUser?.uid!!,
                                        name,
                                        email,
                                        village,
                                        mobile,
                                        count
                                )
                                val firestore = FirebaseFirestore.getInstance().collection("ANMUser")
                                firestore.document(mobile).set(user)

                            }




                        } else {
                            Toast.makeText(context, "Unable to add member", Toast.LENGTH_LONG).show()
                            Log.d(RegisterFragment.TAG, task.exception.toString())
                        }
                    }



            Toast.makeText(context, "Approved.", Toast.LENGTH_LONG).show()
        }


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