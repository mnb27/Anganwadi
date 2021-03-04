package com.example.maternalmortality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.maternalmortality.models.DoctorPatientDetails
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class DataForDoctor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_for_doctor)

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

        val saveButton: Button =findViewById(R.id.saveButton)



        val auth = FirebaseAuth.getInstance()

        saveButton.setOnClickListener {
            var feverText = fever.editText?.text.toString()
            var headacheText = headache.editText?.text.toString()
            var blurvisonText = blurvision.editText?.text.toString()
            var swellingText = swelling.editText?.text.toString()
            var puffedfaceText = puffedface.editText?.text.toString()
            var palpitationsText = palpitations.editText?.text.toString()
            var fatigabilityText = fatigability.editText?.text.toString()
            var breathlessnessText = breathlessness.editText?.text.toString()
            var abdomenpainText = abdomenpain.editText?.text.toString()
            var vaginalbleedingText = vaginalbleeding.editText?.text.toString()
            var waterdischargeText = waterdischarge.editText?.text.toString()
            var fatalmovementsText = fatalmovements.editText?.text.toString()


            val patientDetails = DoctorPatientDetails(feverText,headacheText,blurvisonText,swellingText,puffedfaceText,palpitationsText,fatigabilityText,breathlessnessText,abdomenpainText,vaginalbleedingText,waterdischargeText,fatalmovementsText
                ,auth.currentUser?.uid.toString())
            val firestore = FirebaseFirestore.getInstance().collection("DoctorPatientDetails")

            firestore.document().set(patientDetails)
                .addOnCompleteListener { task->
                    if(task.isSuccessful){
                        Toast.makeText(this, "Successfully Saved",Toast.LENGTH_LONG).show()
                        val intent = Intent(this,AshaActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
                    }
                }


        }


    }


}