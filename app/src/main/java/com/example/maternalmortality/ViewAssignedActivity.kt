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

class ViewAssignedActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var detailsAdapter: ViewAssignedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_assigned)

        val fireStore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()

        var list: MutableList<PatientDetails> = mutableListOf()


        recyclerView = findViewById(R.id.recyclerView1)
        detailsAdapter = ViewAssignedAdapter(this,list)


        recyclerView.adapter = detailsAdapter

        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchToDoList()
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

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        val searchItem: MenuItem = menu?.findItem(R.id.action_search)!!
        val searchView: SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchDetails(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    searchDetails(it)
                }
                return true
            }

        })

        return true
    }

    private fun searchDetails(newText: String) {

        doAsync {
            var list: MutableList<PatientDetails> = mutableListOf()
            val fireStore = FirebaseFirestore.getInstance()
            val auth = FirebaseAuth.getInstance()

            fireStore.collection("PatientDetails")
                    .whereEqualTo("dataTakenBy", auth.currentUser?.uid)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            list.add(document.toObject(PatientDetails::class.java))
                        }
                        (recyclerView.adapter as DetailsAdapter).notifyDataSetChanged()
                    }
                    .addOnFailureListener {
                        Log.e("No","Error")
                    }
            uiThread {
                val filteredList = filter(list, newText)

                filteredList?.let {
                    detailsAdapter.setList(it)
                    recyclerView.scrollToPosition(0)
                }
            }
        }

    }

    private fun filter(list: List<PatientDetails>,newText: String): MutableList<PatientDetails>? {
        val lowerCaseText = newText.toLowerCase()
        val filteredList: MutableList<PatientDetails> = mutableListOf()

        for(item in list) {
            val text: String = item.name?.toLowerCase()!!
            if(text.contains(lowerCaseText)) {
                filteredList.add(item)
            }
        }
        return filteredList
    }*/

    private fun fetchToDoList() {
        doAsync {
            var list: MutableList<PatientDetails> = mutableListOf()
            val fireStore = FirebaseFirestore.getInstance()
            val auth = FirebaseAuth.getInstance()

            fireStore.collection("PatientDetails")
                    .whereEqualTo("anm_supervisior_email", auth.currentUser?.email)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            list.add(document.toObject(PatientDetails::class.java))
                        }
                        (recyclerView.adapter as ViewAssignedAdapter).notifyDataSetChanged()
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