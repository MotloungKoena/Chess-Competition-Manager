package com.ufs.csiq6823.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ufs.csiq6823.GameStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM games WHERE weekTitle = :weekTitle ORDER BY createdAt ASC")
    fun gamesForWeek(weekTitle: String): Flow<List<GameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(game: GameEntity)

    @Query("SELECT * FROM games WHERE id = :id LIMIT 1")
    fun game(id: String): Flow<GameEntity?>

    @Query("UPDATE games SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: String, status: GameStatus)
}


