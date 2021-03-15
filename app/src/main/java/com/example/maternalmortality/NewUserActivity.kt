package com.example.maternalmortality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.maternalmortality.models.New_Registration
import com.example.maternalmortality.models.ANMUser
import com.example.maternalmortality.models.AshaUser

import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase

import android.app.DatePickerDialog
import android.view.View
import android.widget.*
import java.util.*
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_new_user.*

class NewUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        /// date picker start
        val mPickTimeBtn = findViewById<Button>(R.id.pickDateBtn)
        val dob: TextInputLayout = findViewById(R.id.six)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        mPickTimeBtn.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                dob.getEditText()?.setText("" + dayOfMonth + " / " + month + " / " + year)
            }, year, month, day)
            dpd.show()
        }
        //////end


        val name: TextInputLayout = findViewById(R.id.one)
        val email: TextInputLayout = findViewById(R.id.two)
        val center_name: TextInputLayout = findViewById(R.id.three)
        val center_code: TextInputLayout = findViewById(R.id.four)

        val village: TextInputLayout = findViewById(R.id.seven)
        val state: TextInputLayout = findViewById(R.id.eight)
        val mobile: TextInputLayout = findViewById(R.id.nine)

        val saveButton: Button =findViewById(R.id.saveButton)



        val auth = FirebaseAuth.getInstance()

        saveButton.setOnClickListener {
            var nameText = name.editText?.text.toString()
            var emailText = email.editText?.text.toString()
            var center_name_Text = center_name.editText?.text.toString()
            var center_code_Text = center_code.editText?.text.toString()

            if (nameText.isEmpty()) {
                name.setError("Required Field")
                return@setOnClickListener
            }
            if (emailText.isEmpty()) {
                email.setError("Required Field")
                return@setOnClickListener
            }
            if (center_code_Text.isEmpty()) {
                center_code.setError("Required Field")
                return@setOnClickListener
            }

            // radio button
            var category:String
            if(anmRadio.isChecked) category="ANM"
            else category="asha"

            var gender:String
            if(male.isChecked) gender="male"
            else gender="female"

            var dob_text = dob.editText?.text.toString()
            var village_text = village.editText?.text.toString()
            var state_text = state.editText?.text.toString()
            var mobile_text = mobile.editText?.text.toString()
            var status = "pending"

            if (dob_text.isEmpty()) {
                dob.setError("Required Field")
                return@setOnClickListener
            }
            if (village_text.isEmpty()) {
                village.setError("Required Field")
                return@setOnClickListener
            }
            if (mobile_text.isEmpty()) {
                mobile.setError("Required Field")
                return@setOnClickListener
            }

            val New_user = New_Registration(nameText,emailText,center_name_Text,center_code_Text,category,gender,dob_text,village_text,state_text,mobile_text,status)
            val firestore = FirebaseFirestore.getInstance().collection("New_Registration")

            firestore.document(mobile_text).set(New_user)
                    .addOnCompleteListener { task->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Successfully Applied",Toast.LENGTH_LONG).show()
                            val intent = Intent(this,Dashboard::class.java)
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