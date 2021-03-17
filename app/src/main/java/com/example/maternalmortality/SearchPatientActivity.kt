package com.example.maternalmortality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.maternalmortality.models.PatientDetails
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SearchPatientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_patient)


        val searchButton: Button = findViewById(R.id.searchButton)
        val name: TextInputLayout = findViewById(R.id.one)
        val village: TextInputLayout = findViewById(R.id.two)

        val firestore = FirebaseFirestore.getInstance()

        var c= 0
        searchButton.setOnClickListener {
            val nameText = name.editText?.text.toString().toLowerCase()
            val villageText = village.editText?.text.toString().toLowerCase()
            firestore.collection("PatientDetails").get()
                .addOnSuccessListener {documents->
                    for(document in documents){
                        var patDetails = document.toObject(PatientDetails::class.java)
                        if(patDetails.name.toLowerCase() == nameText && patDetails.village.toLowerCase() == villageText){
                            c=1
                        }
                    }

                    if(c==0){
                        Toast.makeText(this,"Patient Not Found",Toast.LENGTH_LONG).show()
                    }

                    else{
                        val intent = Intent(this,ViewSearchedPatient::class.java)
                        intent.putExtra("name",nameText)
                        intent.putExtra("village",villageText)
                        startActivity(intent)
                        finish()
                    }

                }
        }
    }
}