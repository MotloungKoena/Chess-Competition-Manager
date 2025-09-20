package com.ufs.csiq6823

import android.app.Application
import com.ufs.csiq6823.data.GameRepository
import com.ufs.csiq6823.data.PlayerRepository
import com.ufs.csiq6823.data.WeeksRepository
import com.ufs.csiq6823.data.db.AppDatabase

class ChessApp : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
    val playerRepo by lazy { PlayerRepository(db.playerDao()) }
    val gameRepo by lazy { GameRepository(db.gameDao()) }
    val weekRepo by lazy { WeeksRepository(db.weekDao()) }
}
