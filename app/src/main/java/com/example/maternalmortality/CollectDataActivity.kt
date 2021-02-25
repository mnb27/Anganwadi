package com.example.maternalmortality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.maternalmortality.models.PatientDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class CollectDataActivity : AppCompatActivity() {

    var isBeingUpdated = false
    var previousDetails: PatientDetails? = null
    var previousId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collect_data)

        if(intent.hasExtra("previous details")){
            isBeingUpdated = true
            previousDetails = intent.extras?.get("previous details") as PatientDetails
            previousId = intent.extras?.get("Id") as String
        }

        val name: EditText = findViewById(R.id.one)
        val date: EditText = findViewById(R.id.two)
        val age: EditText = findViewById(R.id.three)
        val village: EditText = findViewById(R.id.four)
        val religion: EditText = findViewById(R.id.five)
        val caste: EditText = findViewById(R.id.six)
        val phone: EditText = findViewById(R.id.seven)
        val lpara: EditText = findViewById(R.id.eight)
        val lmp: EditText = findViewById(R.id.nine)
        val edod: EditText = findViewById(R.id.ten)
        val tetanus1: EditText = findViewById(R.id.eleven)
        val tetanus2: EditText = findViewById(R.id.twelve)
        val anc: EditText = findViewById(R.id.thirteen)
        val heartbeat: EditText = findViewById(R.id.fourteen)
        val bp: EditText = findViewById(R.id.fifteen)

        name.setText(previousDetails?.name)
        date.setText(previousDetails?.date)
        age.setText(previousDetails?.age)
        village.setText(previousDetails?.village)
        religion.setText(previousDetails?.religion)
        caste.setText(previousDetails?.caste)
        phone.setText(previousDetails?.phone)
        lpara.setText(previousDetails?.lpara)
        lmp.setText(previousDetails?.lmp)
        edod.setText(previousDetails?.edod)
        tetanus1.setText(previousDetails?.tetanus1)
        tetanus2.setText(previousDetails?.tetanus2)
        anc.setText(previousDetails?.anc)
        heartbeat.setText(previousDetails?.heatbeat)
        bp.setText(previousDetails?.bp)

        val saveButton: Button =findViewById(R.id.saveButton)



        val auth = FirebaseAuth.getInstance()

        saveButton.setOnClickListener {
            var nameText = name.text.toString()
            var dateText = date.text.toString()
            var ageText = age.text.toString()
            var villageText = village.text.toString()
            var religionText = religion.text.toString()
            var casteText = caste.text.toString()
            var phoneText = phone.text.toString()
            var lparaText = lpara.text.toString()
            var lmpText = lmp.text.toString()
            var edodText = edod.text.toString()
            var tetanus1Text = tetanus1.text.toString()
            var tetanus2Text = tetanus2.text.toString()
            var ancText = anc.text.toString()
            var heartbeatText = heartbeat.text.toString()
            var bpText = bp.text.toString()


            val firestore = FirebaseFirestore.getInstance().collection("PatientDetails")

            if(isBeingUpdated){
                val patientDetails = previousId?.let { it1 ->
                    PatientDetails(
                        nameText,
                        dateText,
                        ageText,
                        villageText,
                        religionText,
                        casteText,
                        phoneText,
                        lparaText,
                        lmpText,
                        edodText,
                        tetanus1Text,
                        tetanus2Text
                        ,
                        ancText,
                        heartbeatText,
                        bpText,
                        auth.currentUser?.uid.toString()
                        )



                }
                if (patientDetails != null) {
                    firestore.document(previousId!!).set(patientDetails)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Successfully Updated", Toast.LENGTH_LONG).show()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                            }
                        }
                }

            }
            else {

                val patientDetails = PatientDetails(
                    nameText,
                    dateText,
                    ageText,
                    villageText,
                    religionText,
                    casteText,
                    phoneText,
                    lparaText,
                    lmpText,
                    edodText,
                    tetanus1Text,
                    tetanus2Text
                    ,
                    ancText,
                    heartbeatText,
                    bpText,
                    auth.currentUser?.uid.toString()
                )
                firestore.document(patientDetails.phone).set(patientDetails)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Successfully Saved", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                        }
                    }
            }


        }


    }


}