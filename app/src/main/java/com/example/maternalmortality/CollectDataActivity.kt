package com.example.maternalmortality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.maternalmortality.models.PatientDetails
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class CollectDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collect_data)

        val name: TextInputLayout = findViewById(R.id.one)
        val date: TextInputLayout = findViewById(R.id.two)
        val age: TextInputLayout = findViewById(R.id.three)
        val village: TextInputLayout = findViewById(R.id.four)
        val religion: TextInputLayout = findViewById(R.id.five)
        val caste: TextInputLayout = findViewById(R.id.six)
        val phone: TextInputLayout = findViewById(R.id.seven)
        val lpara: TextInputLayout = findViewById(R.id.eight)
        val lmp: TextInputLayout = findViewById(R.id.nine)
        val edod: TextInputLayout = findViewById(R.id.ten)
        val tetanus1: TextInputLayout = findViewById(R.id.eleven)
        val tetanus2: TextInputLayout = findViewById(R.id.twelve)
        val anc: TextInputLayout = findViewById(R.id.thirteen)
        val heartbeat: TextInputLayout = findViewById(R.id.fourteen)
        val bp: TextInputLayout = findViewById(R.id.fifteen)

        val saveButton: Button =findViewById(R.id.saveButton)



        val auth = FirebaseAuth.getInstance()

        saveButton.setOnClickListener {
            var nameText = name.editText?.text.toString()
            var dateText = date.editText?.text.toString()
            var ageText = age.editText?.text.toString()
            var villageText = village.editText?.text.toString()
            var religionText = religion.editText?.text.toString()
            var casteText = caste.editText?.text.toString()
            var phoneText = phone.editText?.text.toString()
            var lparaText = lpara.editText?.text.toString()
            var lmpText = lmp.editText?.text.toString()
            var edodText = edod.editText?.text.toString()
            var tetanus1Text = tetanus1.editText?.text.toString()
            var tetanus2Text = tetanus2.editText?.text.toString()
            var ancText = anc.editText?.text.toString()
            var heartbeatText = heartbeat.editText?.text.toString()
            var bpText = bp.editText?.text.toString()
            val patientDetails = PatientDetails(nameText,dateText,ageText,villageText,religionText,casteText,phoneText,lparaText,lmpText,edodText,tetanus1Text,tetanus2Text
            ,ancText,heartbeatText,bpText,auth.currentUser?.uid.toString())
            val firestore = FirebaseFirestore.getInstance().collection("PatientDetails")

            firestore.document().set(patientDetails)
                .addOnCompleteListener { task->
                    if(task.isSuccessful){
                        Toast.makeText(this, "Successfully Saved",Toast.LENGTH_LONG).show()
                        val intent = Intent(this,MainActivity::class.java)
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