package com.example.maternalmortality

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.maternalmortality.models.ANMUser
import com.example.maternalmortality.models.AshaUser
import com.example.maternalmortality.models.PatientDetails
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import java.util.*

import kotlin.concurrent.schedule

class CollectDataActivity : AppCompatActivity() {

    var isBeingUpdated = false
    var previousDetails: PatientDetails? = null
    var previousId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collect_data)

        /// date picker start
        val mPickTimeBtn = findViewById<Button>(R.id.pickDateBtn)
        val date: TextInputLayout = findViewById(R.id.two)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        mPickTimeBtn.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                date.getEditText()?.setText("" + dayOfMonth + " / " + month + " / " + year)
            }, year, month, day)
            dpd.show()
        }
        //////end

        if(intent.hasExtra("previous details")){
            isBeingUpdated = true
            previousDetails = intent.extras?.get("previous details") as PatientDetails
            previousId = intent.extras?.get("Id") as String
        }

        val name: TextInputLayout = findViewById(R.id.one)

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

        name.editText?.setText(previousDetails?.name)
        date.editText?.setText(previousDetails?.date)
        age.editText?.setText(previousDetails?.age)
        village.editText?.setText(previousDetails?.village)
        religion.editText?.setText(previousDetails?.religion)
        caste.editText?.setText(previousDetails?.caste)
        phone.editText?.setText(previousDetails?.phone)
        lpara.editText?.setText(previousDetails?.lpara)
        lmp.editText?.setText(previousDetails?.lmp)
        edod.editText?.setText(previousDetails?.edod)
        tetanus1.editText?.setText(previousDetails?.tetanus1)
        tetanus2.editText?.setText(previousDetails?.tetanus2)
        anc.editText?.setText(previousDetails?.anc)
        heartbeat.editText?.setText(previousDetails?.heartbeat)
        bp.editText?.setText(previousDetails?.bp)

        val saveButton: Button =findViewById(R.id.saveButton)

        if(isBeingUpdated){
            saveButton.text = "UPDATE DATA"
        }

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

            var anm_supervisor_email = ""
            var asha_supervisor_email = ""

            val fireStore = FirebaseFirestore.getInstance()

            if (isBeingUpdated) {
                anm_supervisor_email = previousDetails?.anm_supervisior_email.toString()
                asha_supervisor_email = previousDetails?.asha_supervisor_email.toString()

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
                            tetanus2Text,
                            ancText,
                            heartbeatText,
                            bpText,
                            auth.currentUser?.uid.toString(),
                            anm_supervisor_email,
                            asha_supervisor_email
                    )


                }
                if (patientDetails != null) {
                    fireStore.document(previousId!!).set(patientDetails)
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

            } else {
                val auth = FirebaseAuth.getInstance()

                var list: MutableList<AshaUser> = mutableListOf()
                var list1: MutableList<ANMUser> = mutableListOf()

                //
                //Assigning the Asha worker with min patients currently and having same village as of patient
//            var details = list[0]
//            if(list.size>0)
//            {
//                var curr_count_asha = details.count.toInt()
//                asha_supervisor_email = details.email.toString()
//            }
//            print("size of list is ");
//            println(list.size);


                // Here we are updating the count of asha worker............
                // var Docref = FirebaseFirestore.getInstance().collection("AshaUser").document(details.mobile);
                //Docref.update("count" , curr_count_asha+1);


                //Assigning the ANM worker with min patients currently and having same village as of patient

                //
                println(villageText);
                fireStore.collection("ANMUser").whereEqualTo("village", villageText).orderBy("count", Query.Direction.ASCENDING).limit(1).get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        list1.add(document.toObject(ANMUser::class.java))
                    }
                    println("size of list1 is here is ..........");
                    println(list1.size);

                    println("Again printing.......");
                    var details1 = list1[0]
                    println(details1.email);
                    //var curr_count_ANM = details1.count.toInt()

                    anm_supervisor_email = details1.email


                    var query = fireStore.collection("AshaUser").whereEqualTo("village", villageText).orderBy("count", Query.Direction.ASCENDING).limit(1)
                    query.get().addOnSuccessListener { documents ->
                        for (document in documents) {
                            list.add(document.toObject(AshaUser::class.java))
                        }
                        var details = list[0]
                        asha_supervisor_email = details.email

                        var curr_count_anm = details1.count.toInt() + 1
                        var Docref1 = FirebaseFirestore.getInstance().collection("ANMUser").document(details1.mobile);
                        Docref1.update("count", curr_count_anm);


                        var curr_count_asha = details.count.toInt() + 1
                        var Docref = FirebaseFirestore.getInstance().collection("AshaUser").document(details.mobile);
                        Docref.update("count", curr_count_asha);


                        println(anm_supervisor_email);
                        println(asha_supervisor_email);
                        val patientDetails = PatientDetails(nameText, dateText, ageText, villageText, religionText, casteText, phoneText, lparaText, lmpText, edodText, tetanus1Text, tetanus2Text
                                , ancText, heartbeatText, bpText, auth.currentUser?.uid.toString(), asha_supervisor_email, anm_supervisor_email)
                        val firestore = FirebaseFirestore.getInstance().collection("PatientDetails")

                        firestore.document().set(patientDetails)
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
                            .addOnFailureListener {
                                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                            }
                    // Toast.makeText(this, "$(list1.size)", Toast.LENGTH_LONG).show()
                }
                        .addOnFailureListener {
                            print("Error..............aa rhi hai")
                            //       Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                        }

//            println("print ho ja ");
//            println("village of patient is $villageText   ");
//            println("size of list1 is");
//            println(list1.size);
                // println(anm_supervisor_email);
                //Here we are updating the count of ANM worker...............
                // var Docref1 = FirebaseFirestore.getInstance().collection("ANMUser").document(details1.mobile);
                //Docref1.update("count" , curr_count_ANM+1);


                //Now pushing the record of new patient to the collection.............


            }

        }


    }


}

