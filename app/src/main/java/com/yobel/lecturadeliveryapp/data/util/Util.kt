package com.yobel.lecturadeliveryapp.data.util

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Util {

    fun saveListEnterprisesSharePreferences(sharedPreferences: SharedPreferences, data:String){
        sharedPreferences.edit().putString("LIST_ENTERPRISE",data).apply()
    }
    fun saveEnterpriseSharePreferences(sharedPreferences: SharedPreferences, data:String){
        sharedPreferences.edit().putString("ENTERPRISE",data).apply()
    }

    fun getDateTime(): String {
        val fechaHoraActual = LocalDateTime.now()
        val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return fechaHoraActual.format(formato)
    }
}