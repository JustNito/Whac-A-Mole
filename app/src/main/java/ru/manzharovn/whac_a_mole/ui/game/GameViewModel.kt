package ru.manzharovn.whac_a_mole.ui.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ru.manzharovn.whac_a_mole.data.repository.ScoreRepository
import ru.manzharovn.whac_a_mole.model.Hole
import ru.manzharovn.whac_a_mole.utils.AMOUNT_OF_TIME
import ru.manzharovn.whac_a_mole.utils.GameStatus
import ru.manzharovn.whac_a_mole.utils.SCORE_PER_MOLE
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

class GameViewModel (private val repository: ScoreRepository) : ViewModel() {

    private var _gameTimer by mutableStateOf(AMOUNT_OF_TIME)
    val gameTimer: Int
        get() = _gameTimer

    private var _countDown by mutableStateOf(3)
    val countDown: Int
        get() = _countDown

    private var _gameStatus by mutableStateOf(GameStatus.PAUSE)
    val gameStatus: GameStatus
        get() = _gameStatus

    private var _score by mutableStateOf(0)
    val score: Int
        get() = _score

    private val _holes = mutableStateListOf<Hole>()
    val holes: List<Hole>
        get() = _holes

    private lateinit var coroutineContext: CoroutineContext

    init {
        resetHoles()
    }

    private fun resetGame() {
        resetHoles()
        _gameTimer = AMOUNT_OF_TIME
        _countDown = 3
        _score = 0
    }

    fun startGame() {
        coroutineContext = viewModelScope.launch {
            if(gameStatus == GameStatus.END_GAME) {
                resetGame()
            }
            _gameStatus = GameStatus.START_COUNTDOWN
            countDown()
            resetHoles()
            _gameStatus = GameStatus.PLAY
            val puttingProcess = async(Dispatchers.Main) {
                putMoleInHole()
            }
            startTimer()
            puttingProcess.cancel()
            endGame()
            _gameStatus = GameStatus.END_GAME
        }
    }

    fun pauseGame() {
        _gameStatus = GameStatus.PAUSE
        coroutineContext.cancel()
        _countDown = 3
    }

    private suspend fun putMoleInHole() {
        while(true) {
            val index = Random.nextInt(0, 9)
            _holes[index] = _holes[index].copy(hasMole = true)
            delay(500)
            _holes[index] = _holes[index].copy(hasMole = false)
        }

    }

    private fun resetHoles() {
        if(_holes.isNotEmpty())
            _holes.clear()
        for (x in 0 until 9) {
            _holes.add(Hole(false))
        }
    }

    fun tapOnMole(hole: Hole) {
        val index = _holes.indexOf(hole)
        _holes[index] = _holes[index].copy(hasMole = false)
        _score += SCORE_PER_MOLE
    }

    private suspend fun countDown() {
        delay(1000)
        while(_countDown != 0) {
            _countDown -= 1
            delay(1000)
        }
    }

    private fun endGame() {
        changeHighScore()
    }

    private fun changeHighScore() {
        if(_score > repository.getHighScore()) {
            repository.setHighScore(_score)
        }
    }

    private suspend fun startTimer() {
        while(_gameTimer != 0) {
            _gameTimer -= 1
            delay(1000)
        }
    }
}