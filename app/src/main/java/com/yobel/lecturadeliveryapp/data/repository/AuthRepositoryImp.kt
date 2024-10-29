package com.yobel.lecturadeliveryapp.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.jotadev.jetcompose_2024_ii_ecoeats.core.Result
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.endpoints.MethodsApi
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.Enterprise
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.ErrorResponse
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.UserDto
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.WrapperResponse
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.toUser
import com.yobel.lecturadeliveryapp.data.util.Util
import com.yobel.lecturadeliveryapp.domain.model.User
import com.yobel.lecturadeliveryapp.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Named

class AuthRepositoryImp @Inject constructor(
    @Named("provideEnterprisesSharePreferences") val sharedPreferences: SharedPreferences,
    @Named("provideEnterpriseSelectedSharePreferences") val sharedPreferencesEnterpriseSelected: SharedPreferences,
    val api: MethodsApi,
) : AuthRepository {


    override suspend fun signIn(user: String, password: String): Result<User> {
        try {
            val response = api.signIn(user, password)

            if (response.isSuccessful) {
                val data = response.body()
                return if (data?.status == "200") {
                    val userDto = response?.body()?.data
                    val gson = Gson()
                    val jsonString = gson.toJson(userDto?.cia)
                    Util.saveListEnterprisesSharePreferences(sharedPreferences, jsonString)
                    Result.Success(data = userDto?.toUser())
                } else {
                    Result.Error(message = data?.msg ?: "Error desconocido")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                if (response.code() == 404 && !errorBody.isNullOrEmpty()) {
                    // Parsear el JSON de error
                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)

                    return Result.Error(message = errorResponse.msg)
                }
                return Result.Error(message = response.message())
            }
        } catch (ex: Exception) {
            return Result.Error(message = ex.message.toString())
        }

    }

    override suspend fun saveEnterprise(enterprise: Enterprise): Result<String> {
        try {
            val gson = Gson()
            val jsonString = gson.toJson(enterprise)
            Util.saveEnterpriseSharePreferences(sharedPreferencesEnterpriseSelected, jsonString)
            return Result.Success("Empresa ${enterprise.ciaDescripcion} seleccionada.")
        } catch (ex: Exception) {
            return Result.Error(message = ex.message.toString())
        }
    }

}