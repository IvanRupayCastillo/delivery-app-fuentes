package com.yobel.lecturadeliveryapp.data.repository

import com.google.gson.Gson
import com.jotadev.jetcompose_2024_ii_ecoeats.core.Result
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.endpoints.MethodsApi
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.Enterprise
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.ErrorResponse
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.toLabel
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.toLabelList
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.toUser
import com.yobel.lecturadeliveryapp.data.util.Util
import com.yobel.lecturadeliveryapp.domain.model.Label
import com.yobel.lecturadeliveryapp.domain.repository.LabelRepository
import javax.inject.Inject

class LabelRepositoryImp @Inject constructor(
    val api: MethodsApi,
) : LabelRepository {
    override suspend fun getLabelList(cia:String,user:String): Result<List<Label>> {
        try {
            val response = api.getLabelList(cia = cia, user = user)

            if (response.isSuccessful) {
                val data = response.body()
                return if (data?.status == "200") {
                    val labels = response?.body()?.data
                    Result.Success(data = labels?.toLabelList())
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

    override suspend fun readLabel(
        cia: String,
        user: String,
        trackId: String,
        ctr: String,
        container: String
    ): Result<Label> {
        try {
            val response = api.readLabel(
                cia = cia,
                user = user,
                trackId = trackId,
                ctr = ctr,
                container = container
            )

            if (response.isSuccessful) {
                val data = response.body()
                when (data?.status) {
                    "200" -> {
                        val labels = response?.body()?.data
                        return Result.Success(data = labels?.toLabel())
                    }
                    "401" -> {
                        return Result.Error(message = data?.msg ?: "EL PEDIDO LEIDO NO SE ENCUENTRA , REGISTRADO UN EN NUESTRO SISTEMA")
                    }
                    "402" -> {
                        return Result.Error(message = data?.msg ?: "EL PEDIDO AUN NO TIENE UNA CARGA ASIGNADA")
                    }
                    "403" -> {
                        return Result.Error(message = data?.msg ?: "EL PEDIDO YA FUE REGISTRADO")
                    }
                    else -> {
                        return Result.Error(message = data?.msg ?: "Error desconocido")
                    }
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
}