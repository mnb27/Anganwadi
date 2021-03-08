package com.example.maternalmortality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.example.maternalmortality.auth.AuthenticationActivity
import com.example.maternalmortality.models.ANMUser
import com.example.maternalmortality.models.New_Registration
import com.example.maternalmortality.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.jetbrains.anko.doAsync

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)


        val info: CardView = findViewById(R.id.info)

            /*if(list[0].type == "admin") {
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
            }*/


        var auth = FirebaseAuth.getInstance()
        var firestore = FirebaseFirestore.getInstance()

        var c=0

        if(auth.currentUser != null){
            firestore.collection("ANMUser").whereEqualTo("email",auth.currentUser?.email).get()
                .addOnSuccessListener {
                    for(document in it){
                        c=1
                    }
                    if(c==1){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else if(c==0) {
                        val intent = Intent(this, AshaActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

        }


        else {
            val anm: CardView = findViewById(R.id.anmportal)
            val asha: CardView = findViewById(R.id.ashaportal)
            val admin: CardView = findViewById(R.id.adminportal)
            val feedback: CardView = findViewById(R.id.feedback)

            anm.setOnClickListener {
                //GlobalVar.Companion.globalUser = 2;
                val intent = Intent(this, MainActivity::class.java)

                startActivity(intent)
            }
            asha.setOnClickListener {
                //GlobalVar.Companion.globalUser = 3;
                val intent = Intent(this, AshaActivity::class.java)
                startActivity(intent)
            }
            admin.setOnClickListener {
                //GlobalVar.Companion.globalUser = 1;
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
            }

            feedback.setOnClickListener {
                val intent = Intent(this, FeedbackActivity::class.java)
                startActivity(intent)
            }
        }

        info.setOnClickListener {
            val intent = Intent(this, InfoCard::class.java)
            startActivity(intent)
        }

    }
}