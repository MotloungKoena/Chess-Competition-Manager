package com.ufs.csiq6823.data

import com.ufs.csiq6823.PlayerUiState

object PlayerMemoryStore {
    private val store = mutableMapOf<String, PlayerUiState>()

    fun load(id: String): PlayerUiState? = store[id]

    fun save(id: String, state: PlayerUiState) {
        store[id] = state
    }

    fun all(): List<Pair<String, PlayerUiState>> = store.toList()
}
