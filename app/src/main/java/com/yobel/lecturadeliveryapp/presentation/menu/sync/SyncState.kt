package com.yobel.lecturadeliveryapp.presentation.menu.sync

import com.yobel.lecturadeliveryapp.domain.model.Label

data class SyncState(
    val isLoading:Boolean = false,
    val error:String?=null,
    val success: String?=null,
)
