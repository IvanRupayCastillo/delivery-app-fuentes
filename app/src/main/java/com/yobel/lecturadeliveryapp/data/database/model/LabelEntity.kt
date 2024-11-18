package com.yobel.lecturadeliveryapp.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "table_label")
data class LabelEntity(
    @PrimaryKey(autoGenerate = false)
    @Nonnull
    val trackId:String,
    @ColumnInfo(name = "sequence")
    val sequence:String,
    @ColumnInfo(name = "zone1")
    val zone1:String,
    @ColumnInfo(name = "zone2")
    val zone2:String,
    @ColumnInfo(name = "route")
    val route:String,
    @ColumnInfo(name = "upload")
    val upload:String,
    @ColumnInfo(name = "container")
    val container:String,
    @ColumnInfo(name = "date")
    val date:String,
    @ColumnInfo(name = "status")
    val status:String,
    @ColumnInfo(name = "read_date")
    val readDate:String,
)