package com.example.maternalmortality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.maternalmortality.models.New_feedback
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class FeedbackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        val q1: TextInputLayout = findViewById(R.id.one)
        val q2: TextInputLayout = findViewById(R.id.two)
        val q3: TextInputLayout = findViewById(R.id.three)
        val  q4: TextInputLayout = findViewById(R.id.four)



        val saveButton: Button =findViewById(R.id.saveButton)



        val auth = FirebaseAuth.getInstance()

        saveButton.setOnClickListener {
            var q1 = q1.editText?.text.toString()
            var q2 = q2.editText?.text.toString()
            var q3 = q3.editText?.text.toString()
            var q4 = q4.editText?.text.toString()
            val New_user = New_feedback(q1,q2,q3,q4)
            val firestore = FirebaseFirestore.getInstance().collection("New_feedback")

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