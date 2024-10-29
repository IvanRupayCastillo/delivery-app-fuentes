package com.yobel.lecturadeliveryapp.domain.model

import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.Enterprise

data class User(
    val name:String,
    val enterprises:List<Enterprise>
)
