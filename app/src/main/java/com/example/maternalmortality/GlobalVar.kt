package com.example.maternalmortality

import android.app.Application

class GlobalVar: Application() {
    companion object {
        var globalUser = -1
//        1-admin 2-anm 3-asha
    }

    override fun onCreate() {
        super.onCreate()
        // initialization code here
    }
}