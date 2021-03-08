package com.example.maternalmortality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
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



        val signOutButton: CardView = findViewById(R.id.button)

        val collectData: CardView = findViewById(R.id.button3)

        val viewAssigned: CardView = findViewById(R.id.button6)

        viewAssigned.setOnClickListener{
            val intent = Intent(this, ViewAssignedActivity::class.java)
            startActivity(intent)
        }



        collectData.setOnClickListener {
            val intent = Intent(this, CollectDataActivity::class.java)
            startActivity(intent)
        }

        val viewData: CardView  = findViewById(R.id.button5)

        viewData.setOnClickListener {
            val intent = Intent(this, ViewDataIndividualActivity::class.java)
            startActivity(intent)
        }




        



        signOutButton.setOnClickListener{
            auth.signOut()

            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
            finish()
        }
    }

}