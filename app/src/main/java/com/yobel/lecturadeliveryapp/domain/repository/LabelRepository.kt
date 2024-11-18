package com.yobel.lecturadeliveryapp.domain.repository

import com.jotadev.jetcompose_2024_ii_ecoeats.core.Result
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.Enterprise
import com.yobel.lecturadeliveryapp.domain.model.Label
import kotlinx.coroutines.flow.Flow

interface LabelRepository {

    suspend fun getLabelList(cia:String,user:String) : Result<List<Label>>
    suspend fun readLabel(cia:String,user:String,trackId:String,ctr:String,container:String) : Result<Label>
    suspend fun syncData(cia:String,date:String) : Result<String>
    fun getCounterPending(): Flow<Int>
    suspend fun readLabelDatabase(trackId:String) : Result<Label>
    suspend fun syncDataRemote()
    suspend fun syncDataManuallyRemote(): Result<String>
}