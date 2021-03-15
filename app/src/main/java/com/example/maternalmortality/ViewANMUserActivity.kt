package com.example.maternalmortality

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maternalmortality.models.ANMUser
import com.example.maternalmortality.models.AshaUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ViewANMUserActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var detailsAdapter: ViewANMUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_a_n_m_user)

        val fireStore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()

        var list: MutableList<ANMUser> = mutableListOf()


        recyclerView = findViewById(R.id.recyclerViewANMUser)
        detailsAdapter = ViewANMUserAdapter(this, list)


        recyclerView.adapter = detailsAdapter

        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchToDoList()

    }

    private fun fetchToDoList() {
        doAsync {
            var list: MutableList<ANMUser> = mutableListOf()
            val fireStore = FirebaseFirestore.getInstance()
            val auth = FirebaseAuth.getInstance()

            fireStore.collection("ANMUser")

                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        list.add(document.toObject(ANMUser::class.java))
                    }
                    (recyclerView.adapter as ViewANMUserAdapter).notifyDataSetChanged()
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