package com.ufs.csiq6823.data

import com.ufs.csiq6823.GameStatus
import com.ufs.csiq6823.data.db.GameDao
import com.ufs.csiq6823.data.db.GameEntity
import kotlinx.coroutines.flow.Flow

class GameRepository(private val dao: GameDao) {
    fun gamesForWeek(weekTitle: String): Flow<List<GameEntity>> = dao.gamesForWeek(weekTitle)

    fun game(id: String): Flow<GameEntity?> = dao.game(id)

    suspend fun add(entity: GameEntity) = dao.insert(entity)

    suspend fun addGamee(weekTitle: String, white: String, black: String) {
        dao.insert(GameEntity(weekTitle = weekTitle, white = white, black = black))
    }

    suspend fun setResult(id: String, status: GameStatus) {
        dao.updateStatus(id, status)
    }
}
