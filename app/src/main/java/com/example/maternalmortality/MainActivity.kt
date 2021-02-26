package com.example.maternalmortality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.maternalmortality.auth.AuthenticationActivity
import com.example.maternalmortality.models.PatientDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this, AuthenticationActivity::class.java))
            finish()
        }

        val auth = FirebaseAuth.getInstance()



        val signOutButton: Button = findViewById(R.id.button)

        val collectData: Button = findViewById(R.id.button3)

        collectData.setOnClickListener {
            val intent = Intent(this, CollectDataActivity::class.java)
            startActivity(intent)
        }

        val viewData: Button = findViewById(R.id.button5)

        viewData.setOnClickListener {
            val intent = Intent(this, ViewDataIndividualActivity::class.java)
            startActivity(intent)
        }
        



        signOutButton.setOnClickListener{
            auth.signOut()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}