package com.example.maternalmortality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.maternalmortality.auth.AuthenticationActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this, AuthenticationActivity::class.java))
            finish()
        }

         val bundle:Bundle?=intent.extras
         val nam = bundle?.get("useremail")
        print(nam)
         if(nam=="admin@gmail.com")
         {
             startActivity(Intent(this, AdminActivity::class.java))
             finish()
         }




        val signOutButton: Button = findViewById(R.id.button)

        val collectData: Button = findViewById(R.id.button3)

        collectData.setOnClickListener {
            val intent = Intent(this, CollectDataActivity::class.java)
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