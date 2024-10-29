package com.yobel.lecturadeliveryapp.data.util

import android.content.SharedPreferences

object Util {

    fun saveListEnterprisesSharePreferences(sharedPreferences: SharedPreferences, data:String){
        sharedPreferences.edit().putString("LIST_ENTERPRISE",data).apply()
    }
    fun saveEnterpriseSharePreferences(sharedPreferences: SharedPreferences, data:String){
        sharedPreferences.edit().putString("ENTERPRISE",data).apply()
    }
}