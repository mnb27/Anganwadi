package com.example.maternalmortality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maternalmortality.models.PatientDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import android.widget.Button
import androidx.cardview.widget.CardView
import com.example.maternalmortality.auth.AuthenticationActivity


class AshaPatients : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var ashapatientsAdapter: AshaPatientsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asha_patients)
        val fireStore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()

        val user_email = auth.currentUser?.email

        var list: MutableList<PatientDetails> = mutableListOf()


        recyclerView = findViewById(R.id.asha_patients)
        ashapatientsAdapter = AshaPatientsAdapter(this,list)


        recyclerView.adapter = ashapatientsAdapter

        recyclerView.layoutManager = LinearLayoutManager(this)

        fireStore.collection("PatientDetails").whereEqualTo("asha_supervisor_email",user_email).get()
            .addOnSuccessListener { documents->
                for(document in documents) {
                    list.add(document.toObject(PatientDetails::class.java))
                }
                (recyclerView.adapter as AshaPatientsAdapter).notifyDataSetChanged()
                if(list.isEmpty()) {
                    Toast.makeText(this,"No patients assigned",Toast.LENGTH_LONG).show()
                    val intent = Intent(this, AshaActivity::class.java)
                    startActivity(intent)
                }
            }
            .addOnFailureListener{
                Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
            }
    }
}