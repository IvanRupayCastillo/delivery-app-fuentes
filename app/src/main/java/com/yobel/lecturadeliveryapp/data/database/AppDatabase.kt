package com.yobel.lecturadeliveryapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yobel.lecturadeliveryapp.data.database.dao.LabelDao
import com.yobel.lecturadeliveryapp.data.database.model.LabelEntity

@Database(
    entities = [LabelEntity::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun labelDao() : LabelDao

}