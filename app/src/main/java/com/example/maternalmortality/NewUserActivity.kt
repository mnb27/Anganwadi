package com.example.maternalmortality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.maternalmortality.models.New_Registration
import com.example.maternalmortality.models.ANMUser
import com.example.maternalmortality.models.AshaUser

import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase

class NewUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        val name: TextInputLayout = findViewById(R.id.one)
        val email: TextInputLayout = findViewById(R.id.two)
        val center_name: TextInputLayout = findViewById(R.id.three)
        val center_code: TextInputLayout = findViewById(R.id.four)
        val gender: TextInputLayout = findViewById(R.id.five)
        val dob: TextInputLayout = findViewById(R.id.six)
        val village: TextInputLayout = findViewById(R.id.seven)
        val state: TextInputLayout = findViewById(R.id.eight)
        val mobile: TextInputLayout = findViewById(R.id.nine)
        val category:TextInputLayout = findViewById(R.id.ten)



        val saveButton: Button =findViewById(R.id.saveButton)



        val auth = FirebaseAuth.getInstance()

        saveButton.setOnClickListener {
            var name = name.editText?.text.toString()
            var email = email.editText?.text.toString()
            var center_name = center_name.editText?.text.toString()
            var center_code = center_code.editText?.text.toString()
            var category = category.editText?.text.toString()
            var gender = gender.editText?.text.toString()
            var dob = dob.editText?.text.toString()
            var village = village.editText?.text.toString()
            var state = state.editText?.text.toString()
            var mobile = mobile.editText?.text.toString()
            var status = "pending"
            val New_user = New_Registration(name,email,center_name,center_code,category,gender,dob,village,state,mobile,status)
            val firestore = FirebaseFirestore.getInstance().collection("New_Registration")

            firestore.document(mobile).set(New_user)
                .addOnCompleteListener { task->
                    if(task.isSuccessful){
                        Toast.makeText(this, "Successfully Applied",Toast.LENGTH_LONG).show()
                        val intent = Intent(this,AdminActivity::class.java)
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