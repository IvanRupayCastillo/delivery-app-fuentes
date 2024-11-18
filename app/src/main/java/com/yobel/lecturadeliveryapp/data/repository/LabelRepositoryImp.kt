package com.yobel.lecturadeliveryapp.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.jotadev.jetcompose_2024_ii_ecoeats.core.Result
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.endpoints.MethodsApi
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.Enterprise
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.ErrorResponse
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.SyncRequest
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.toEntityLabel
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.toLabel
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.toLabelEntity
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.toLabelList
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.toUser
import com.yobel.lecturadeliveryapp.data.database.dao.LabelDao
import com.yobel.lecturadeliveryapp.data.util.Util
import com.yobel.lecturadeliveryapp.domain.model.Label
import com.yobel.lecturadeliveryapp.domain.repository.LabelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Named

class LabelRepositoryImp @Inject constructor(
    val api: MethodsApi,
    val dao: LabelDao,
    @Named("provideDataSharePreferences") val sharedPreferences: SharedPreferences,
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

    override suspend fun syncData(cia: String, date: String): Result<String> {
        try {
            val counter = dao.checkPending()

            if(counter>0){
                return Result.Error(message = "Tiene $counter pedidos por enviar al servidor antes de sincronizar nuevamente.")
            }

            val response = api.downloadRemoteData(cia = cia, date = date)

            if (response.isSuccessful) {
                val data = response.body()
                if (data?.status == "200") {
                    val labels = response?.body()?.data
                    labels?.let {
                        if(it.isNotEmpty()){
                            dao.insertLabels(it.toLabelEntity())
                        }
                    }
                    return Result.Success(data = "Sincronizado correctamente")
                } else {
                    return Result.Error(message = data?.msg ?: "Error desconocido")
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

    override fun getCounterPending(): Flow<Int>  {
        return dao.checkPendingRealTime()
    }

    override suspend fun readLabelDatabase(trackId: String): Result<Label> {
        try {
            val validate = dao.validateReadLabel(trackId)

            if(validate>0){
                return Result.Error(message = "El pedido $trackId ya fue leido")
            }

            val response = dao.readLabel(trackId,Util.getDateTime())

            if (response == 1) {
                val data = dao.getLabelByTrackId(trackId)
                data?.let {
                    return Result.Success(data = data.toEntityLabel())
                }?: kotlin.run {
                    return Result.Error(message = "Hubo un error al obtener el pedido $trackId")
                }
            } else {
                return Result.Error(message = "No se ha encontrado el pedido $trackId")
            }

        } catch (ex: Exception) {
            return Result.Error(message = ex.message.toString())
        }
    }

    override suspend fun syncDataRemote() {
        try {

            val cia = sharedPreferences.getString("CIA","") ?: ""
            val user = sharedPreferences.getString("USER","") ?: ""

            val labels = dao.getLabelsSync()

            val labelsRequest = labels.map {
                SyncRequest(
                    track_id = it.trackId,
                    contenedor = it.container,
                    fecha_hora =  it.readDate,
                    usuario = user
                )
            }

            labels?.let {
                val response = api.syncRemoteData(
                    cia = cia,
                    labelsRequest
                )
                if(response.isSuccessful){
                    val responseData = response.body()
                    if(responseData?.status == "200"){
                        labels?.let {
                            it.forEach {
                                dao.updateLabelSync(it.trackId)
                            }
                        }

                    }
                }
            }

        } catch (ex: Exception) {

        }
    }

    override suspend fun syncDataManuallyRemote() : Result<String> {
        try {

            val cia = sharedPreferences.getString("CIA","") ?: ""
            val user = sharedPreferences.getString("USER","") ?: ""

            val labels = dao.getLabelsSync()

            if(labels.isEmpty()){
                return Result.Error(message = "No hay pedidos pendientes por enviar")
            }

            val labelsRequest = labels.map {
                SyncRequest(
                    track_id = it.trackId,
                    contenedor = it.container,
                    fecha_hora =  it.readDate,
                    usuario = user
                )
            }
            val response = api.syncRemoteData(
                cia = cia,
                labelsRequest
            )
            if(response.isSuccessful){
                val responseData = response.body()
                if(responseData?.status == "200"){
                    labels?.let {
                        it.forEach {
                            dao.updateLabelSync(it.trackId)
                        }
                    }
                    return Result.Success(data = "Sincronización terminada.")
                }else{
                    return Result.Error(message = "No hemos podido sincronizar sus pedidos")
                }
            }else{
                return Result.Error(message = response.message())
            }
        } catch (ex: Exception) {
            return Result.Error(message = ex.message.toString())
        }
    }
}