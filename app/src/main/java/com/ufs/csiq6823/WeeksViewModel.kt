package com.ufs.csiq6823

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ufs.csiq6823.data.PlayerRepository
import com.ufs.csiq6823.data.WeeksRepository
import com.ufs.csiq6823.data.db.WeekEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

data class WeekUI(val title: String, val subtitle: String, val isOpen: Boolean)

class WeeksViewModel(private val repo: WeeksRepository) : ViewModel() {
    private val dateFmt = SimpleDateFormat("MMM d, yyyy", Locale.US)

    val weeks: StateFlow<List<WeekUI>> =
        repo.weeks().map { list ->
            list.map { e ->
                WeekUI(
                    title = e.title,
                    subtitle = dateFmt.format(Date(e.dateMillis)),
                    isOpen = e.status.equals("Open", ignoreCase = true)
                )
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun saveWeek(title: String, dateMillis: Long, status: String) {
        viewModelScope.launch { repo.addWeek(title, dateMillis, status) }
    }
    fun addWeek(title: String, dateMillis: Long, status: String) =
        viewModelScope.launch { repo.addWeek(title, dateMillis, status) }
    companion object {
        fun Factory(repo: WeeksRepository) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                WeeksViewModel(repo) as T
        }
    }
}
