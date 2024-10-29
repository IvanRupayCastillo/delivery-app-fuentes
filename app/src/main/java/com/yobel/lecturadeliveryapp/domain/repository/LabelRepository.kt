package com.yobel.lecturadeliveryapp.domain.repository

import com.jotadev.jetcompose_2024_ii_ecoeats.core.Result
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.Enterprise
import com.yobel.lecturadeliveryapp.domain.model.Label

interface LabelRepository {

    suspend fun getLabelList(cia:String,user:String) : Result<List<Label>>
    suspend fun readLabel(cia:String,user:String,trackId:String,ctr:String,container:String) : Result<Label>
}