package com.yobel.lecturadeliveryapp.data.repository

import android.content.SharedPreferences
import com.yobel.lecturadeliveryapp.domain.repository.HomeRepository
import javax.inject.Inject
import javax.inject.Named

class HomeRepositoryImp @Inject constructor(
    @Named("provideDataSharePreferences") val sharedPreferences: SharedPreferences,
) : HomeRepository {

    override suspend fun saveData(cia:String,user:String) {
        sharedPreferences.edit().apply {
            putString("CIA",cia)
            putString("USER",user)
        }.apply()
    }
}