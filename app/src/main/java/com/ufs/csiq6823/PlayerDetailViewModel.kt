import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ufs.csiq6823.data.PlayerRepository
import kotlinx.coroutines.launch

class PlayerDetailViewModel(private val repo: PlayerRepository) : ViewModel() {

    fun save(full: String, nick: String, grade: Int?) {
        viewModelScope.launch {

                repo.save(full, nick, grade)

        }
    }

    companion object {
        fun factory(repo: PlayerRepository) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PlayerDetailViewModel(repo) as T
            }
        }
    }
}
