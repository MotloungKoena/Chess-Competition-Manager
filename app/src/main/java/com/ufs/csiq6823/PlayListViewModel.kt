package com.ufs.csiq6823

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ufs.csiq6823.data.GameRepository
import com.ufs.csiq6823.data.db.GameEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PlayListViewModel(private val repo: GameRepository) : ViewModel() {

    fun gamesForWeek(weekTitle: String): Flow<List<GameEntity>> =
        repo.gamesForWeek(weekTitle)

    fun addGame(weekTitle: String, white: String, black: String) {
        viewModelScope.launch {
            repo.add(GameEntity(weekTitle = weekTitle, white = white, black = black))
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val repo: GameRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            PlayListViewModel(repo) as T
    }
}
