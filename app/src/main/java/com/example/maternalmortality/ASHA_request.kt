package com.example.maternalmortality


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maternalmortality.models.New_Registration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ASHA_request : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var ashadetailsAdapter: AshaDetailsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_s_h_a_request)
        val fireStore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()

        var list: MutableList<New_Registration> = mutableListOf()


        recyclerView = findViewById(R.id.recyclerView)
        ashadetailsAdapter = AshaDetailsAdapter(this,list)


        recyclerView.adapter = ashadetailsAdapter

        recyclerView.layoutManager = LinearLayoutManager(this)

            fireStore.collection("New_Registration").whereEqualTo("category","asha").whereEqualTo("status","pending").get()
                .addOnSuccessListener { documents->
                    for(document in documents) {
                        list.add(document.toObject(New_Registration::class.java))
                    }
                    (recyclerView.adapter as AshaDetailsAdapter).notifyDataSetChanged()
                }
                .addOnFailureListener{
                    Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
                }







    }
}