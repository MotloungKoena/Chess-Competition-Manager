package com.ufs.csiq6823.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeekDao {
    @Insert
    suspend fun insert(week: WeekEntity): Long

    @Query("SELECT * FROM weeks ORDER BY dateMillis DESC")
    fun observeWeeks(): Flow<List<WeekEntity>>
}
