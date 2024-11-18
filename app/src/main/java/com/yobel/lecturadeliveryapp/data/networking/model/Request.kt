package com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model

data class SyncRequest(
    val track_id:String,
    val contenedor:String,
    val fecha_hora:String,
    val usuario:String
)


