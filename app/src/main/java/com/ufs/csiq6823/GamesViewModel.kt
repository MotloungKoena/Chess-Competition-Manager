package com.ufs.csiq6823

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ufs.csiq6823.data.GameRepository
import com.ufs.csiq6823.data.db.GameEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GamesViewModel(private val repo: GameRepository) : ViewModel() {

    fun gamesForWeek(week: String) = repo.gamesForWeek(week)

    fun addGame(weekTitle: String, white: String, black: String) {
        viewModelScope.launch {
            repo.add(GameEntity(weekTitle = weekTitle, white = white, black = black))
        }
    }
    fun game(id: String): Flow<GameEntity?> = repo.game(id)

    fun setResult(id: String, status: GameStatus) = viewModelScope.launch {
        repo.setResult(id, status)
    }

    companion object {
        fun factory(repo: GameRepository) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return GamesViewModel(repo) as T
            }
        }
    }
}
