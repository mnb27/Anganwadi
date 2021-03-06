package com.example.maternalmortality

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.widget.Button
import androidx.cardview.widget.CardView
import com.example.maternalmortality.auth.AuthenticationActivity
import com.google.firebase.auth.FirebaseAuth

class AshaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asha)

        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this, AuthenticationActivity::class.java))
            finish()
        }

        val bundle:Bundle?=intent.extras
        val nam = bundle?.get("useremail")
        print(nam)
//        if(nam=="admin@gmail.com")
//        {
//            startActivity(Intent(this, AdminActivity::class.java))
//            finish()
//        }

        val signOutButton: CardView = findViewById(R.id.button)

        val assignDoctor: CardView = findViewById(R.id.button3)

        val viewAssigned: CardView = findViewById(R.id.button4)

        assignDoctor.setOnClickListener {
            val intent = Intent(this, DataForDoctor::class.java)
            startActivity(intent)
        }

        viewAssigned.setOnClickListener {
            val intent = Intent(this, AshaPatients::class.java)
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