package com.example.maternalmortality.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.maternalmortality.*
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginFragment : Fragment() {

    companion object {
        const val TAG = "LoginFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    // function to finish fragment
    private fun finishActivity() {
        if (activity != null) {
            activity!!.finish()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val goToRegister: TextView = view.findViewById(R.id.go_to_register)

        goToRegister.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(
                    R.id.auth_fragment_container,
                    RegisterFragment()
                )
                ?.addToBackStack(null)
                ?.commit()
        }

        val emailText: TextInputLayout = view.findViewById(R.id.email_text)
        val passwordText: TextInputLayout = view.findViewById(R.id.password_text)

        val loginButton: Button = view.findViewById(R.id.login_button)

        val loginProgress: ProgressBar = view.findViewById(R.id.login_progress)

        loginButton.setOnClickListener {
            val email = emailText.editText?.text.toString()
            val password = passwordText.editText?.text.toString()

            emailText.error = null
            passwordText.error = null

            if (TextUtils.isEmpty(email)) {
                emailText.error = "Email is required"
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailText.error = "Please enter a valid email address"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                passwordText.error = "Password is required"
                return@setOnClickListener
            }

            loginProgress.visibility = View.VISIBLE

            var user = GlobalVar.Companion.globalUser;

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if(task.isSuccessful) {
                        /*val intent  = Intent(activity, Dashboard::class.java)
                        startActivity(intent)
                        finishActivity()*/

                        var auth = FirebaseAuth.getInstance()
                        var firestore = FirebaseFirestore.getInstance()

                        var c=0

                        if(auth.currentUser != null){

                            if(auth.currentUser?.email == "admin@gmail.com") {
                                val intent = Intent(activity, AdminActivity::class.java)
                                startActivity(intent)
                                finishActivity()
                            }
                            else  {
                                firestore.collection("ANMUser").whereEqualTo("email",auth.currentUser?.email).get()
                                    .addOnSuccessListener {
                                        for(document in it){
                                            c=1
                                        }
                                        if(c==1){
                                            val intent = Intent(activity, MainActivity::class.java)
                                            startActivity(intent)
                                            finishActivity()
                                        }
                                        else if(c==0) {
                                            val intent = Intent(activity, AshaActivity::class.java)
                                            startActivity(intent)
                                            finishActivity()
                                        }
                                    }
                            }
                        }

                    }
                    /*if (task.isSuccessful && user==2) {
                        val intent  = Intent(activity, MainActivity::class.java)
                        intent.putExtra("useremail",email)
                        startActivity(intent)
                    }
                    else if (task.isSuccessful && user==1) {
                        val intent  = Intent(activity, AdminActivity::class.java)
                        intent.putExtra("useremail",email)
                        startActivity(intent)
                    }
                    else if (task.isSuccessful && user==3) {
                        val intent  = Intent(activity, AshaActivity::class.java)
                        intent.putExtra("useremail",email)
                        startActivity(intent)
                    }*/
                     else {
                        Toast.makeText(context, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show()
                        Log.d(TAG, task.exception.toString())
                    }
                }
        }
    }
}