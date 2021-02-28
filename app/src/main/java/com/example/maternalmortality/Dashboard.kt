package com.example.maternalmortality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.example.maternalmortality.auth.AuthenticationActivity
import com.google.firebase.auth.FirebaseAuth

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val anm: CardView = findViewById(R.id.anmportal)
        val asha: CardView = findViewById(R.id.ashaportal)
        val admin: CardView = findViewById(R.id.adminportal)
        val feedback: CardView = findViewById(R.id.feedback)


        anm.setOnClickListener {
            GlobalVar.Companion.globalUser = 2;
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        asha.setOnClickListener {
            GlobalVar.Companion.globalUser = 3;
            val intent = Intent(this, AshaActivity::class.java)
            startActivity(intent)
        }
        admin.setOnClickListener {
            GlobalVar.Companion.globalUser = 1;
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }

        feedback.setOnClickListener {
            val intent = Intent(this, FeedbackActivity::class.java)
            startActivity(intent)
        }

    }
}