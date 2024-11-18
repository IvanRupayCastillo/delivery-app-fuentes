package com.yobel.lecturadeliveryapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yobel.lecturadeliveryapp.data.database.model.LabelEntity
import kotlinx.coroutines.flow.Flow

//S = Cuando sincroniza con el servidor
//R = Cuando se lee con el codigo de barras
//T = Cuando se envia al servidor
@Dao
interface LabelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLabels(labels:List<LabelEntity>)

    @Query("SELECT COUNT(*) FROM table_label where status='R'")
    fun checkPending():Int

    @Query("SELECT COUNT(*) FROM table_label where status='R'")
    fun checkPendingRealTime():Flow<Int>

    @Query("UPDATE table_label SET status = 'R', read_date = :readDate WHERE trackId = :trackId")
    fun readLabel(trackId: String, readDate: String): Int

    @Query("SELECT * FROM table_label WHERE trackId = :trackId")
    fun getLabelByTrackId(trackId: String): LabelEntity?

    @Query("SELECT COUNT(*) FROM table_label where status = 'R' AND trackId = :trackId")
    fun validateReadLabel(trackId: String): Int

    @Query("SELECT * FROM table_label where status = 'R'")
    fun getLabelsSync():List<LabelEntity>

    @Query("UPDATE table_label SET status = 'T' WHERE trackId = :trackId")
    fun updateLabelSync(trackId: String):Int
}