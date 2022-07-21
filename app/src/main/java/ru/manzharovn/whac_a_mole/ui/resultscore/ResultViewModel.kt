package ru.manzharovn.whac_a_mole.ui.resultscore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.manzharovn.whac_a_mole.data.repository.ScoreRepository

class ResultViewModel (private val repository: ScoreRepository) : ViewModel() {

    private var _highScore by mutableStateOf(0)
    val highScore: Int
        get() = _highScore

    fun updateHighScore() {
        _highScore = repository.getHighScore()
    }

}