package com.ufs.csiq6823

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ufs.csiq6823.data.PlayerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class PlayerRowUi(
    val id: Long,
    val name: String,
)

class PlayersViewModel(private val repo: PlayerRepository) : ViewModel() {

    val rows: StateFlow<List<PlayerRowUi>> =
        repo.observeAll()
            .map { list ->
                list.map { p ->
                    PlayerRowUi(
                        id = p.id  ,
                        name = p.fullName,
                    )
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    companion object {
        fun factory(repo: PlayerRepository) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                PlayersViewModel(repo) as T
        }
    }
}
