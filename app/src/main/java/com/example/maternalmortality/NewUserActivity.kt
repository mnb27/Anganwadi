package com.example.maternalmortality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.maternalmortality.models.New_Registration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class NewUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        val name: EditText = findViewById(R.id.one)
        val email: EditText = findViewById(R.id.two)
        val center_name: EditText = findViewById(R.id.three)
        val center_code: EditText = findViewById(R.id.four)
        val gender: EditText = findViewById(R.id.five)
        val dob: EditText = findViewById(R.id.six)
        val village: EditText = findViewById(R.id.seven)
        val state: EditText = findViewById(R.id.eight)
        val mobile: EditText = findViewById(R.id.nine)
        val category:EditText = findViewById(R.id.ten)



        val saveButton: Button =findViewById(R.id.saveButton)



        val auth = FirebaseAuth.getInstance()

        saveButton.setOnClickListener {
            var name = name.text.toString()
            var email = email.text.toString()
            var center_name = center_name.text.toString()
            var center_code = center_code.text.toString()
            var category = category.text.toString()
            var gender = gender.text.toString()
            var dob = dob.text.toString()
            var village = village.text.toString()
            var state = state.text.toString()
            var mobile = mobile.text.toString()
            var status = "pending"
            val New_user = New_Registration(name,email,center_name,center_code,category,gender,dob,village,state,mobile,status)
            val firestore = FirebaseFirestore.getInstance().collection("New_Registration")

            firestore.document().set(New_user)
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