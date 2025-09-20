package com.ufs.csiq6823.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.ufs.csiq6823.GameStatus
import java.util.UUID

@Entity(
    tableName = "games",
    indices = [Index("weekTitle")]
)
data class GameEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val weekTitle: String,
    val white: String,
    val black: String,
    val status: GameStatus = GameStatus.Pending,
    val createdAt: Long = System.currentTimeMillis()
)
