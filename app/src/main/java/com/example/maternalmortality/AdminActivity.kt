package com.example.maternalmortality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.cardview.widget.CardView
import com.example.maternalmortality.auth.AuthenticationActivity
import com.google.firebase.auth.FirebaseAuth

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        val signOutButton: CardView = findViewById(R.id.button)
        val newpost: CardView = findViewById(R.id.button1)
        val anmrequest: CardView = findViewById(R.id.button2)
        val asharequest: CardView = findViewById(R.id.button3)
        val feedback: CardView = findViewById(R.id.feedback)

        feedback.setOnClickListener {
            val intent = Intent(this, FeedbackActivity::class.java)
            startActivity(intent)
        }
        newpost.setOnClickListener {
            val intent = Intent(this, NewUserActivity::class.java)
            startActivity(intent)
        }
        anmrequest.setOnClickListener {
            val intent = Intent(this, ANMrequest::class.java)
            startActivity(intent)
        }
        asharequest.setOnClickListener {
            val intent = Intent(this, ASHA_request::class.java)
            startActivity(intent)
        }


        val firebase = FirebaseAuth.getInstance()

        signOutButton.setOnClickListener{
            firebase.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}