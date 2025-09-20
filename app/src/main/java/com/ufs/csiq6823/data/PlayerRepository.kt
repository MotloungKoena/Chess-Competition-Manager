package com.ufs.csiq6823.data

import com.ufs.csiq6823.data.db.PlayerDao
import com.ufs.csiq6823.data.db.PlayerEntity
import kotlinx.coroutines.flow.Flow

class PlayerRepository(private val dao: PlayerDao) {
    fun observeAll(): Flow<List<PlayerEntity>> = dao.observeAll()
    fun observeById(id: Long): Flow<PlayerEntity?> = dao.observeById(id)
    suspend fun upsert(p: PlayerEntity): Long = dao.upsert(p)
    suspend fun getById(id: Long): PlayerEntity? = dao.getById(id)
    suspend fun delete(p: PlayerEntity) = dao.delete(p)
    suspend fun save(full: String, nick: String,   grade: Int?) {
        val entity = PlayerEntity(
            id = 0,
            fullName = full,
            nickName = nick,
            grade = grade
        )
        dao.upsert(entity)
    }

}
