package com.example.maternalmortality

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maternalmortality.models.AshaUser
import com.example.maternalmortality.models.PatientDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ViewAshaUserActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var detailsAdapter: ViewAshaUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_asha_user)

        val fireStore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()

        var list: MutableList<AshaUser> = mutableListOf()


        recyclerView = findViewById(R.id.recyclerViewAshaUser)
        detailsAdapter = ViewAshaUserAdapter(this, list)


        recyclerView.adapter = detailsAdapter

        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchToDoList()

    }

    private fun fetchToDoList() {
        doAsync {
            var list: MutableList<AshaUser> = mutableListOf()
            val fireStore = FirebaseFirestore.getInstance()
            val auth = FirebaseAuth.getInstance()

            fireStore.collection("AshaUser")

                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        list.add(document.toObject(AshaUser::class.java))
                    }
                    (recyclerView.adapter as ViewAshaUserAdapter).notifyDataSetChanged()
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