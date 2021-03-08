package com.example.maternalmortality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
                Toast.makeText(this,"list"+previousDetails.fever, Toast.LENGTH_LONG).show()

                val fever: TextInputLayout = findViewById(R.id.one)
                val headache: TextInputLayout = findViewById(R.id.two)
                val blurvision: TextInputLayout = findViewById(R.id.three)
                val swelling: TextInputLayout = findViewById(R.id.four)
                val puffedface: TextInputLayout = findViewById(R.id.five)
                val palpitations: TextInputLayout = findViewById(R.id.six)
                val fatigability: TextInputLayout = findViewById(R.id.seven)
                val breathlessness: TextInputLayout = findViewById(R.id.eight)
                val abdomenpain: TextInputLayout = findViewById(R.id.nine)
                val vaginalbleeding: TextInputLayout = findViewById(R.id.ten)
                val waterdischarge: TextInputLayout = findViewById(R.id.eleven)
                val fatalmovements: TextInputLayout = findViewById(R.id.twelve)

                fever.editText?.setText(previousDetails?.fever)
                headache.editText?.setText(previousDetails?.headache)
                blurvision.editText?.setText(previousDetails?.blurring_Vision)
                swelling.editText?.setText(previousDetails?.swelling)
                puffedface.editText?.setText(previousDetails?.puffed_face)
                palpitations.editText?.setText(previousDetails?.palpitations)
                fatigability.editText?.setText(previousDetails?.fatigability)
                breathlessness.editText?.setText(previousDetails?.breathlessness_At_rest)
                abdomenpain.editText?.setText(previousDetails?.abdomen_Pain)
                vaginalbleeding.editText?.setText(previousDetails?.vaginal_Bleeding)
                waterdischarge.editText?.setText(previousDetails?.water_Discharge)
                fatalmovements.editText?.setText(previousDetails?.reduced_fatalMovements)


                fever.isEnabled = false
                headache.isEnabled = false
                blurvision.isEnabled = false
                swelling.isEnabled = false
                puffedface.isEnabled = false
                palpitations.isEnabled = false
                fatigability.isEnabled = false
                breathlessness.isEnabled = false
                abdomenpain.isEnabled = false
                vaginalbleeding.isEnabled = false
                waterdischarge.isEnabled = false
                fatalmovements.isEnabled = false




            }
            .addOnFailureListener{
                print("Error")
            }


    }
}