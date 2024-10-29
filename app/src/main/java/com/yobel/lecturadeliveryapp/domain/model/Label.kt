package com.yobel.lecturadeliveryapp.domain.model


data class Label(
    val sequence:String,
    val zone1:String,
    val zone2:String,
    val route:String,
    val upload:String,
    val trackId:String,
    val container:String,
)
