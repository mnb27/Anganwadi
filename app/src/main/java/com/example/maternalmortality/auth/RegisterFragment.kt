package com.example.maternalmortality.auth

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
import com.example.maternalmortality.MainActivity
import com.example.maternalmortality.R
import com.example.maternalmortality.models.User
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment() {

    companion object {
        const val TAG = "RegisterFragment"
    }

    val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailText: TextInputLayout = view.findViewById(R.id.email_text)
        val nameText: TextInputLayout = view.findViewById(R.id.name_text)
        val passwordText: TextInputLayout = view.findViewById(R.id.password_text)
        val confirmPasswordText: TextInputLayout = view.findViewById(R.id.confirm_password_text)

        val signUpButton: Button = view.findViewById(R.id.signup_button)
        val goToLogin: TextView = view.findViewById(R.id.go_to_login)

        val signUpProgress: ProgressBar = view.findViewById(R.id.sign_up_progress)

        signUpButton.setOnClickListener {
            val email = emailText.editText?.text.toString()
            val name = nameText.editText?.text.toString()
            val password = passwordText.editText?.text.toString()
            val confirmPassword = confirmPasswordText.editText?.text.toString()

            emailText.error = null
            nameText.error = null
            passwordText.error = null
            confirmPasswordText.error = null

            if (TextUtils.isEmpty(email)) {
                emailText.error = "Email is required"
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailText.error = "Please enter a valid email address"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(name)) {
                nameText.error = "Name is required"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                passwordText.error = "Password is required"
                return@setOnClickListener
            }

            if (!password.matches(passwordRegex)) {
                passwordText.error = "Password should contain minimum eight characters, at least one uppercase letter, one lowercase letter and one number"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(confirmPassword)) {
                confirmPasswordText.error = "Confirm password is required"
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                confirmPasswordText.error = "Passwords do not match"
                return@setOnClickListener
            }

            signUpProgress.visibility = View.VISIBLE

            val auth = FirebaseAuth.getInstance()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = User(
                            auth.currentUser?.uid!!,
                            name,
                            email
                        )
                        val firestore = FirebaseFirestore.getInstance().collection("Users")
                        firestore.document(auth.currentUser?.uid!!).set(user)
                            .addOnCompleteListener { task2 ->
                                if (task2.isSuccessful) {
                                    val intent = Intent(activity, MainActivity::class.java)
                                    startActivity(intent)
                                    //startActivity(Intent(activity, MainActivity::class.java))
                                } else {
                                    Toast.makeText(context, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show()
                                    Log.d(TAG, task2.exception.toString())
                                }
                            }

                        signUpProgress.visibility = View.GONE
                    } else {
                        signUpProgress.visibility = View.GONE
                        Toast.makeText(context, task.exception.toString(), Toast.LENGTH_LONG).show()
                        Log.d(TAG, task.exception.toString())
                    }
                }
        }

        goToLogin.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(
                    R.id.auth_fragment_container,
                    LoginFragment()
                )
                ?.commit()
        }
    }
}