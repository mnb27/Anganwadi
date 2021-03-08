package com.example.maternalmortality.models

import java.io.Serializable

data class PatientDetails(val name: String = "",
                          val date: String = "",
                          val age: String = "",val village: String = "",val religion: String = "",val caste: String = "",val phone: String = "",val lpara: String = ""
                          ,val lmp: String = "",val edod: String = "",val tetanus1: String = "",val tetanus2: String = "",val anc: String = "",val heartbeat: String = "",
                          val bp: String = "",val dataTakenBy: String = "",var asha_supervisor_email: String ="",var anm_supervisior_email: String ="" ): Serializable