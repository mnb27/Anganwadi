package com.example.maternalmortality

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.maternalmortality.models.PatientDetails
import com.google.android.material.textfield.TextInputLayout

class ViewMoreDataActivity : AppCompatActivity() {

    var previousDetails: PatientDetails? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_more_data)

        previousDetails = intent.extras?.get("previous details") as PatientDetails
        val name: TextInputLayout = findViewById(R.id.one)
        val date: TextInputLayout = findViewById(R.id.two)
        val age: TextInputLayout = findViewById(R.id.three)
        val village: TextInputLayout = findViewById(R.id.four)
        val religion: TextInputLayout = findViewById(R.id.five)
        val caste: TextInputLayout = findViewById(R.id.six)
        val phone: TextInputLayout = findViewById(R.id.seven)
        val lpara: TextInputLayout = findViewById(R.id.eight)
        val lmp: TextInputLayout = findViewById(R.id.nine)
        val edod: TextInputLayout = findViewById(R.id.ten)
        val tetanus1: TextInputLayout = findViewById(R.id.eleven)
        val tetanus2: TextInputLayout = findViewById(R.id.twelve)
        val anc: TextInputLayout = findViewById(R.id.thirteen)
        val heartbeat: TextInputLayout = findViewById(R.id.fourteen)
        val bp: TextInputLayout = findViewById(R.id.fifteen)

        name.editText?.setText(previousDetails?.name)
        date.editText?.setText(previousDetails?.date)
        age.editText?.setText(previousDetails?.age)
        village.editText?.setText(previousDetails?.village)
        religion.editText?.setText(previousDetails?.religion)
        caste.editText?.setText(previousDetails?.caste)
        phone.editText?.setText(previousDetails?.phone)
        lpara.editText?.setText(previousDetails?.lpara)
        lmp.editText?.setText(previousDetails?.lmp)
        edod.editText?.setText(previousDetails?.edod)
        tetanus1.editText?.setText(previousDetails?.tetanus1)
        tetanus2.editText?.setText(previousDetails?.tetanus2)
        anc.editText?.setText(previousDetails?.anc)
        heartbeat.editText?.setText(previousDetails?.heartbeat)
        bp.editText?.setText(previousDetails?.bp)

        name.isEnabled = false
        date.isEnabled = false
        age.isEnabled = false
        village.isEnabled = false
        religion.isEnabled = false
        caste.isEnabled = false
        phone.isEnabled = false
        lpara.isEnabled = false
        lmp.isEnabled = false
        edod.isEnabled = false
        tetanus1.isEnabled = false
        tetanus2.isEnabled = false
        anc.isEnabled = false
        heartbeat.isEnabled = false
        bp.isEnabled = false

    }
}