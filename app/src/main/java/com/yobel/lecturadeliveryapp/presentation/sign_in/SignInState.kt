package com.yobel.lecturadeliveryapp.presentation.sign_in

import com.yobel.lecturadeliveryapp.domain.model.User


data class SignInState(
    val isLoading:Boolean = false,
    val error:String?=null,
    val success: User?=null,
    val successEnterprise: String?=null
)
