package com.ufs.csiq6823.data

import com.ufs.csiq6823.data.db.WeekDao
import com.ufs.csiq6823.data.db.WeekEntity
import kotlinx.coroutines.flow.Flow

class WeeksRepository(private val dao: WeekDao) {
    fun weeks(): Flow<List<WeekEntity>> = dao.observeWeeks()
    suspend fun addWeek(title: String, dateMillis: Long, status: String) {
        dao.insert(WeekEntity(title = title, dateMillis = dateMillis, status = status))
    }
    suspend fun insert(title: String, dateMillis: Long, status: String) {
        dao.insert(
            WeekEntity(
                title = title.trim(),
                dateMillis = dateMillis,
                status = status
            )
        )
    }
}
