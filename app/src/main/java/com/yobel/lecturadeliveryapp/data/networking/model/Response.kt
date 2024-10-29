package com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WrapperResponse<T>(
    val msg:String,
    val status:String,
    val data: T
)

data class WrapperListResponse<T>(
    val msg:String,
    val status:String,
    val data: List<T>
)

data class UserDto(
    @SerializedName("Usuario") val user:String,
    @SerializedName("Cia") val cia:List<Enterprise>,
)

data class Enterprise(
    @SerializedName("CIACIA") val ciaId:String,
    @SerializedName("CIADES") val ciaDescripcion:String,
)

data class LabelDto(
    @SerializedName("Secuencia") val sequence:String,
    @SerializedName("Zona1") val zone1:String,
    @SerializedName("Zona2") val zone2:String,
    @SerializedName("Ruta") val route:String,
    @SerializedName("Carga") val upload:String,
    @SerializedName("TrackId") val trackId:String,
    @SerializedName("Contenedor") val container:String,
)

data class ErrorResponse(
    val msg: String,
    val status: String,
    val data: Any? // Puedes ajustar este tipo según la estructura real del campo "data"
)