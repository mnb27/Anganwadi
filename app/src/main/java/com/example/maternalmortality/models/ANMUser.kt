package com.example.maternalmortality.models

import java.io.Serializable

data class ANMUser(val id: String = "",
                   val name: String = "",
                   val email: String = "" ,
                   val village: String ="",val mobile:String = "",val count:Int =0,val type: String="ANM"): Serializable