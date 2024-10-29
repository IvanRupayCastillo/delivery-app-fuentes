package com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.endpoints

import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.LabelDto
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.UserDto
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.WrapperListResponse
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.WrapperResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MethodsApi {

    @GET("ServicosLecturaPedidos/Service.svc/GetValidarUsuario")
    suspend fun signIn(
        @Query("codigo") codigo: String,
        @Query("password") password:String
    ) : Response<WrapperResponse<UserDto>>

    @POST("ServicosLecturaPedidos/Service.svc/RegistrarLectura")
    suspend fun readLabel(
        @Query("Cia") cia: String,
        @Query("Usuario") user:String,
        @Query("TrackId") trackId:String,
        @Query("Ctr") ctr:String,
        @Query("Contenedor") container:String,
    ) : Response<WrapperResponse<LabelDto>>

    @GET("ServicosLecturaPedidos/Service.svc/ListadoLectura")
    suspend fun getLabelList(
        @Query("Cia") cia: String,
        @Query("Usuario") user:String
    ) : Response<WrapperListResponse<LabelDto>>





}