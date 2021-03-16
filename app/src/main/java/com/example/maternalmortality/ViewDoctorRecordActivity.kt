package com.example.maternalmortality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.maternalmortality.models.DoctorPatientDetails
import com.example.maternalmortality.models.PatientDetails
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ViewDoctorRecordActivity : AppCompatActivity() {

    var details: PatientDetails? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_doctor_record)

        details = intent.extras?.get("id") as PatientDetails

        var list: MutableList<DoctorPatientDetails> = mutableListOf()
        val firestore = FirebaseFirestore.getInstance().collection("DoctorPatientDetails")
        firestore.whereEqualTo("name",details?.name).whereEqualTo("village",details?.village)
            .whereEqualTo("anm_supervisior_email", FirebaseAuth.getInstance().currentUser?.email)
            .get()
            .addOnSuccessListener { documents->
                for(document in documents) {
                    list.add(document.toObject(DoctorPatientDetails::class.java))
                }

                if(list.isEmpty()){
                    Toast.makeText(this,"Error", Toast.LENGTH_LONG).show()
                    return@addOnSuccessListener
                }

                var previousDetails: DoctorPatientDetails = list[0]


                val fever: TextInputLayout = findViewById(R.id.one)
                val headache: Button = findViewById(R.id.two)
                val blurvision: Button = findViewById(R.id.three)
                val swelling: Button = findViewById(R.id.four)
                val puffedface: Button  = findViewById(R.id.five)
                val palpitations: Button  = findViewById(R.id.six)
                val fatigability: Button  = findViewById(R.id.seven)
                val breathlessness: Button  = findViewById(R.id.eight)
                val abdomenpain: Button  = findViewById(R.id.nine)
                val vaginalbleeding: Button  = findViewById(R.id.ten)
                val waterdischarge: Button = findViewById(R.id.eleven)
                val fatalmovements: Button = findViewById(R.id.twelve)

                fever.editText?.setText(previousDetails?.fever)

                if(previousDetails?.headache == "YES"){
                    headache.setBackgroundResource(R.drawable.rounded_button_green)

                } else {
                    headache.setBackgroundResource(R.drawable.rounded_button_blue)
                }
                if(previousDetails?.blurring_Vision == "YES"){
                    blurvision.setBackgroundResource(R.drawable.rounded_button_green)

                } else {
                    blurvision.setBackgroundResource(R.drawable.rounded_button_blue)
                }


                if(previousDetails?.swelling == "YES"){
                    swelling.setBackgroundResource(R.drawable.rounded_button_green)

                } else {
                    swelling.setBackgroundResource(R.drawable.rounded_button_blue)
                }

                if(previousDetails?.puffed_face == "YES"){
                    puffedface.setBackgroundResource(R.drawable.rounded_button_green)

                } else {
                    puffedface.setBackgroundResource(R.drawable.rounded_button_blue)
                }
                if(previousDetails?.palpitations == "YES"){
                    palpitations.setBackgroundResource(R.drawable.rounded_button_green)

                } else {
                    palpitations.setBackgroundResource(R.drawable.rounded_button_blue)
                }

                if(previousDetails?.fatigability == "YES"){

                    fatigability.setBackgroundResource(R.drawable.rounded_button_green)

                } else {
                    fatigability.setBackgroundResource(R.drawable.rounded_button_blue)
                }
                if(previousDetails?.breathlessness_At_rest == "YES"){
                    breathlessness.setBackgroundResource(R.drawable.rounded_button_green)

                } else {
                    breathlessness.setBackgroundResource(R.drawable.rounded_button_blue)
                }
                if(previousDetails?.abdomen_Pain == "YES"){
                    abdomenpain.setBackgroundResource(R.drawable.rounded_button_green)

                } else {
                    abdomenpain.setBackgroundResource(R.drawable.rounded_button_blue)
                }
                if(previousDetails?.vaginal_Bleeding == "YES"){
                    vaginalbleeding.setBackgroundResource(R.drawable.rounded_button_green)

                } else {
                    vaginalbleeding.setBackgroundResource(R.drawable.rounded_button_blue)
                }
                if(previousDetails?.water_Discharge == "YES"){
                    waterdischarge.setBackgroundResource(R.drawable.rounded_button_green)

                } else {
                    waterdischarge.setBackgroundResource(R.drawable.rounded_button_blue)
                }
                if(previousDetails?.reduced_fatalMovements == "YES"){
                    fatalmovements.setBackgroundResource(R.drawable.rounded_button_green)

                } else {
                    fatalmovements.setBackgroundResource(R.drawable.rounded_button_blue)
                }


                fever.isEnabled = false




            }
            .addOnFailureListener{
                print("Error")
            }


    }
}