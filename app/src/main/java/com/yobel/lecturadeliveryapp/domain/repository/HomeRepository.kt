package com.yobel.lecturadeliveryapp.domain.repository

import com.jotadev.jetcompose_2024_ii_ecoeats.core.Result
import com.yobel.lecturadeliveryapp.domain.model.Label

interface HomeRepository {

    suspend fun saveData(cia:String,user:String)

}