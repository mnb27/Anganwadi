package com.example.maternalmortality.models

import java.io.Serializable

data class DoctorPatientDetails(val fever: String = "",val name: String = "", val village: String = "",
                            val headache: String = "",
                            val blurring_Vision: String = "",val swelling: String = "",val puffed_face: String ="",val palpitations: String = "",val fatigability: String = "",val breathlessness_At_rest: String = "",val abdomen_Pain: String = ""
                            ,val vaginal_Bleeding: String = "",val water_Discharge: String ="",val reduced_fatalMovements: String ="",val dataTakenBy: String = "",val anm_supervisior_email: String =""
                            ,val asha_supervisior_email:String =""): Serializable