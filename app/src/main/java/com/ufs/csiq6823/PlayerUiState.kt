package com.ufs.csiq6823

data class PlayerUiState(
    val fullName: String = "",
    val school: String = "",
    val startingRank: Int = 5
)

sealed interface PlayerIntent {
    data class NameChanged(val value: String) : PlayerIntent
    data class SchoolChanged(val value: String) : PlayerIntent
    data class StartRankChanged(val value: Int) : PlayerIntent
}
