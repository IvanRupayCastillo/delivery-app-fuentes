package com.yobel.lecturadeliveryapp.presentation.menu.read_label

import com.yobel.lecturadeliveryapp.domain.model.Label

data class ReadLabelState(
    val isLoading:Boolean = false,
    val error:String?=null,
    val success: Label?=null,
    val counter:Int=0
)
