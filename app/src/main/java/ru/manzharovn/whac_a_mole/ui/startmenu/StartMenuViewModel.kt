package ru.manzharovn.whac_a_mole.ui.startmenu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.manzharovn.whac_a_mole.data.repository.ScoreRepository
import ru.manzharovn.whac_a_mole.utils.GameStatus
import javax.inject.Inject


class StartMenuViewModel (private val repository: ScoreRepository) : ViewModel() {

    private var _highScore by mutableStateOf(repository.getHighScore())
    val highScore: Int
        get() = _highScore

    fun updateHighScore() {
        _highScore = repository.getHighScore()
    }
}