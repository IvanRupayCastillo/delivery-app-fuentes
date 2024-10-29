package com.yobel.lecturadeliveryapp.domain.repository

import com.jotadev.jetcompose_2024_ii_ecoeats.core.Result
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.Enterprise
import com.yobel.lecturadeliveryapp.domain.model.User

interface AuthRepository {

    suspend fun signIn(user:String,password:String) : Result<User>
    suspend fun saveEnterprise(enterprise: Enterprise) : Result<String>

}