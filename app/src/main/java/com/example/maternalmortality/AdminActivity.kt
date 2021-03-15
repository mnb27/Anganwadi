package com.example.maternalmortality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.cardview.widget.CardView
import com.example.maternalmortality.auth.AuthenticationActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_admin.*

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        val signOutButton: CardView = findViewById(R.id.button)
        val feedback: CardView = findViewById(R.id.feedback)
        val anmrequest: CardView = findViewById(R.id.button2)
        val asharequest: CardView = findViewById(R.id.button3)

        val viewANM: CardView = findViewById(R.id.viewANM)
        val viewAsha: CardView = findViewById(R.id.viewAsha)


        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this, AuthenticationActivity::class.java))
            finish()
        }

        anmrequest.setOnClickListener {
            val intent = Intent(this, ANMrequest::class.java)
            startActivity(intent)
        }
        asharequest.setOnClickListener {
            val intent = Intent(this, ASHA_request::class.java)
            startActivity(intent)
        }
        feedback.setOnClickListener {
            val intent = Intent(this, FeedbackActivity::class.java)
            startActivity(intent)
        }

        viewANM.setOnClickListener {
            val intent = Intent(this,ViewANMUserActivity::class.java)
            startActivity(intent)
        }

        viewAsha.setOnClickListener {
            val intent = Intent(this,ViewAshaUserActivity::class.java)
            startActivity(intent)
        }


        val firebase = FirebaseAuth.getInstance()

        signOutButton.setOnClickListener{
            firebase.signOut()
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
            finish()
        }
    }


}