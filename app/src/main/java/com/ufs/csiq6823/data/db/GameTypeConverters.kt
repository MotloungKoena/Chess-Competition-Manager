package com.ufs.csiq6823.data.db

import androidx.room.TypeConverter
import com.ufs.csiq6823.GameStatus

class GameTypeConverters {
    @TypeConverter fun toStatus(name: String): GameStatus = GameStatus.valueOf(name)
    @TypeConverter
    fun fromStatus(s: GameStatus): String = s.name
}