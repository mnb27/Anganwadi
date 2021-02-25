package com.example.maternalmortality

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

class ViewDataIndividualActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var detailsAdapter: DetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_data_individual)

        val fireStore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()

        var list: MutableList<PatientDetails> = mutableListOf()


        recyclerView = findViewById(R.id.recyclerView)
        detailsAdapter = DetailsAdapter(this,list)


        recyclerView.adapter = detailsAdapter

        recyclerView.layoutManager = LinearLayoutManager(this)

        fireStore.collection("PatientDetails")
            .whereEqualTo("dataTakenBy",auth.currentUser?.uid)
            .get()
            .addOnSuccessListener { documents->
                for(document in documents) {
                    list.add(document.toObject(PatientDetails::class.java))
                }
                (recyclerView.adapter as DetailsAdapter).notifyDataSetChanged()
            }
            .addOnFailureListener{
                Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
            }
        /*.addSnapshotListener{snapshot,e ->

            if(snapshot != null){
                val documents = snapshot.documents
                documents.forEach{
                    val details = it.toObject(PatientDetails::class.java)
                    if(details != null){
                        Log.w("Yes","jfrgbf")
                        list.add(details)
                    }
                    else{
                        Log.e("No","hgt")
                    }
                }
            }
        }*/

    }
}