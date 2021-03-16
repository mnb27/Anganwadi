package com.example.maternalmortality

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.SearchEvent
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maternalmortality.models.PatientDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ViewSearchedPatient : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var detailsAdapter: ViewSearchedPatientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_searched_patient)

        val fireStore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()

        var list: MutableList<PatientDetails> = mutableListOf()


        var name = intent.extras?.get("name") as String
        var village = intent.extras?.get("village") as String
        recyclerView = findViewById(R.id.recyclerViewSearchPatient)
        detailsAdapter = ViewSearchedPatientAdapter(this,list)


        recyclerView.adapter = detailsAdapter

        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchToDoList(name,village)
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



    private fun fetchToDoList(name: String, village: String) {
        doAsync {

            var list: MutableList<PatientDetails> = mutableListOf()
            val fireStore = FirebaseFirestore.getInstance()
            val auth = FirebaseAuth.getInstance()

            fireStore.collection("PatientDetails")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        var patDetails = document.toObject(PatientDetails::class.java)
                        if(patDetails.name.toLowerCase() == name && patDetails.village.toLowerCase() == village){
                            list.add(patDetails)
                        }
                    }
                    (recyclerView.adapter as ViewSearchedPatientAdapter).notifyDataSetChanged()
                }
                .addOnFailureListener {
                    Log.e("No","Error")
                }

            uiThread {
                detailsAdapter.setList(list)
            }
        }
    }


}