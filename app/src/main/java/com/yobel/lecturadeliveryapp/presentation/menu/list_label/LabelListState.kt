package com.yobel.lecturadeliveryapp.presentation.menu.list_label

import com.yobel.lecturadeliveryapp.domain.model.Label

data class LabelListState(
    val isLoading:Boolean = false,
    val error:String?=null,
    val success: List<Label>?=null,
)
