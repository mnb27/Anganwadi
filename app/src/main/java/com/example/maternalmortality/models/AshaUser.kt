package com.example.maternalmortality.models

import java.io.Serializable

data class AshaUser(val id: String = "",
                    val name: String = "",
                    val email: String = "" ,
                    val village: String ="",val mobile:String = "",val count:Int =0,val type: String = "asha"): Serializable

