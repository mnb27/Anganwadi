package com.example.maternalmortality

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.maternalmortality.models.DoctorPatientDetails
import com.example.maternalmortality.models.PatientDetails
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class DataForDoctor : AppCompatActivity() {

    var previousDetails: PatientDetails? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_for_doctor)

        previousDetails = intent.extras?.get("previous details") as PatientDetails

        val firestore = FirebaseFirestore.getInstance()



        var c = 0

        firestore.collection("DoctorPatientDetails").whereEqualTo("name",previousDetails?.name)
            .whereEqualTo("village", previousDetails?.village).whereEqualTo("asha_supervisior_email",FirebaseAuth.getInstance().currentUser?.email)
            .get()
            .addOnSuccessListener { documents->
                /*val intent = Intent(this, Dashboard::class.java)


                startActivity(intent)*/
                    for(document in documents) {
                        c=1
                    }
                    if(c==1) {
                        Toast.makeText(this,"Data Already taken",Toast.LENGTH_LONG).show()
                        val intent = Intent(this, AshaPatients::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        val fever: TextInputLayout = findViewById(R.id.one)
                        val headache: Button = findViewById(R.id.two)
                        val blurvision: Button = findViewById(R.id.three)
                        val swelling: Button = findViewById(R.id.four)
                        val puffedface: Button = findViewById(R.id.five)
                        val palpitations: Button = findViewById(R.id.six)
                        val fatigability: Button = findViewById(R.id.seven)
                        val breathlessness: Button = findViewById(R.id.eight)
                        val abdomenpain: Button = findViewById(R.id.nine)
                        val vaginalbleeding: Button = findViewById(R.id.ten)
                        val waterdischarge: Button = findViewById(R.id.eleven)
                        val fatalmovements: Button = findViewById(R.id.twelve)

                        val saveButton: Button = findViewById(R.id.saveButton)

                        // color change on button click
                        var clickHeadache = true
                        headache.setOnClickListener(View.OnClickListener {
                            clickHeadache = if (clickHeadache) {
//                                headache.setBackgroundColor(Color.parseColor("#228B22"))
                                headache.setBackgroundResource(R.drawable.rounded_button_green)
                                false
                            } else {
                                headache.setBackgroundResource(R.drawable.rounded_button_blue)
                                true
                            }
                        })

                        var clickblurvision = true
                        blurvision.setOnClickListener(View.OnClickListener {
                            clickblurvision = if (clickblurvision) {
                                blurvision.setBackgroundResource(R.drawable.rounded_button_green)
                                false
                            } else {
                                blurvision.setBackgroundResource(R.drawable.rounded_button_blue)
                                true
                            }
                        })

                        var clickswelling = true
                        swelling.setOnClickListener(View.OnClickListener {
                            clickswelling = if (clickswelling) {
                                swelling.setBackgroundResource(R.drawable.rounded_button_green)
                                false
                            } else {
                                swelling.setBackgroundResource(R.drawable.rounded_button_blue)
                                true
                            }
                        })

                        var clickpuffedface = true
                        puffedface.setOnClickListener(View.OnClickListener {
                            clickpuffedface = if (clickpuffedface) {
                                puffedface.setBackgroundResource(R.drawable.rounded_button_green)
                                false
                            } else {
                                puffedface.setBackgroundResource(R.drawable.rounded_button_blue)
                                true
                            }
                        })

                        var clickpalpitations = true
                        palpitations.setOnClickListener(View.OnClickListener {
                            clickpalpitations = if (clickpalpitations) {
                                palpitations.setBackgroundResource(R.drawable.rounded_button_green)
                                false
                            } else {
                                palpitations.setBackgroundResource(R.drawable.rounded_button_blue)
                                true
                            }
                        })

                        var clickfatigability = true
                        fatigability.setOnClickListener(View.OnClickListener {
                            clickfatigability = if (clickfatigability) {
                                fatigability.setBackgroundResource(R.drawable.rounded_button_green)
                                false
                            } else {
                                fatigability.setBackgroundResource(R.drawable.rounded_button_blue)
                                true
                            }
                        })

                        var clickbreathlessness = true
                        breathlessness.setOnClickListener(View.OnClickListener {
                            clickbreathlessness = if (clickbreathlessness) {
                                breathlessness.setBackgroundResource(R.drawable.rounded_button_green)
                                false
                            } else {
                                breathlessness.setBackgroundResource(R.drawable.rounded_button_blue)
                                true
                            }
                        })

                        var clickabdomenpain = true
                        abdomenpain.setOnClickListener(View.OnClickListener {
                            clickabdomenpain = if (clickabdomenpain) {
                                abdomenpain.setBackgroundResource(R.drawable.rounded_button_green)
                                false
                            } else {
                                abdomenpain.setBackgroundResource(R.drawable.rounded_button_blue)
                                true
                            }
                        })

                        var clickvaginalbleeding = true
                        vaginalbleeding.setOnClickListener(View.OnClickListener {
                            clickvaginalbleeding = if (clickvaginalbleeding) {
                                vaginalbleeding.setBackgroundResource(R.drawable.rounded_button_green)
                                false
                            } else {
                                vaginalbleeding.setBackgroundResource(R.drawable.rounded_button_blue)
                                true
                            }
                        })

                        var clickwaterdischarge = true
                        waterdischarge.setOnClickListener(View.OnClickListener {
                            clickwaterdischarge = if (clickwaterdischarge) {
                                waterdischarge.setBackgroundResource(R.drawable.rounded_button_green)
                                false
                            } else {
                                waterdischarge.setBackgroundResource(R.drawable.rounded_button_blue)
                                true
                            }
                        })

                        var clickfatalmovements = true
                        fatalmovements.setOnClickListener(View.OnClickListener {
                            clickfatalmovements = if (clickfatalmovements) {
                                fatalmovements.setBackgroundResource(R.drawable.rounded_button_green)
                                false
                            } else {
                                fatalmovements.setBackgroundResource(R.drawable.rounded_button_blue)
                                true
                            }
                        })
                        ///////////////////////////////////////////////END

                        var anm_email = previousDetails?.anm_supervisior_email.toString()
                        var asha_email = previousDetails?.asha_supervisor_email.toString()
                        var name = previousDetails?.name.toString()
                        var village = previousDetails?.village.toString()


                        val auth = FirebaseAuth.getInstance()

                        saveButton.setOnClickListener {
                            var feverText = fever.editText?.text.toString()

                            if (feverText.isEmpty()) {
                                fever.setError("Required Field")
                                return@setOnClickListener
                            }

                            var headacheText = "NO"
                            if(clickHeadache==false) headacheText="YES"

                            var blurvisonText = "NO"
                            if(clickblurvision==false) blurvisonText="YES"

                            var swellingText = "NO"
                            if(clickswelling==false) swellingText="YES"

                            var puffedfaceText = "NO"
                            if(clickpuffedface==false) puffedfaceText="YES"

                            var palpitationsText = "NO"
                            if(clickpalpitations==false) palpitationsText="YES"

                            var fatigabilityText = "NO"
                            if(clickfatigability==false) fatigabilityText="YES"

                            var breathlessnessText = "NO"
                            if(clickbreathlessness==false) breathlessnessText="YES"

                            var abdomenpainText = "NO"
                            if(clickabdomenpain==false) abdomenpainText="YES"

                            var vaginalbleedingText = "NO"
                            if(clickvaginalbleeding==false) vaginalbleedingText="YES"

                            var waterdischargeText = "NO"
                            if(clickwaterdischarge==false) waterdischargeText="YES"

                            var fatalmovementsText = "NO"
                            if(clickfatalmovements==false) fatalmovementsText="YES"

                            val patientDetails = DoctorPatientDetails(
                                feverText,
                                name,
                                village,
                                headacheText,
                                blurvisonText,
                                swellingText,
                                puffedfaceText,
                                palpitationsText,
                                fatigabilityText,
                                breathlessnessText,
                                abdomenpainText,
                                vaginalbleedingText,
                                waterdischargeText,
                                fatalmovementsText
                                ,
                                auth.currentUser?.uid.toString(),
                                anm_email,
                                asha_email
                            )
                            val firestore = FirebaseFirestore.getInstance().collection("DoctorPatientDetails")

                            firestore.document().set(patientDetails)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(this, "Successfully Saved", Toast.LENGTH_LONG).show()
                                        val intent = Intent(this, AshaPatients::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                                    }
                                }


                        }

                    }








            }
            .addOnFailureListener {
                val intent = Intent(this, AshaPatients::class.java)

                startActivity(intent)
                Log.e("No", "Error")
            }



        }


}