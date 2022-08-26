package ru.manzharovn.whac_a_mole.ui.game

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.*
import ru.manzharovn.whac_a_mole.R
import ru.manzharovn.whac_a_mole.data.repository.ScoreRepository
import ru.manzharovn.whac_a_mole.model.Hole
import ru.manzharovn.whac_a_mole.model.MoleStatus
import ru.manzharovn.whac_a_mole.utils.*
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

    private var _currentFrame by mutableStateOf(R.drawable.ic_hole)
    val currentFrame: Int
        get() = _currentFrame

    private lateinit var coroutineContext: CoroutineContext
    private lateinit var animationJob: Job

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
            startMoleAnimation(index,true)
            animationJob = viewModelScope.launch {
                delay(500)
                startMoleAnimation(index, false)
            }
            animationJob.join()
        }
    }

    private fun resetHoles() {
        if(_holes.isNotEmpty())
            _holes.clear()
        for (x in 0 until 9) {
            _holes.add(Hole(MoleStatus.None))
        }
    }

    fun tapOnMole(hole: Hole) = viewModelScope.launch {
        if(hole.moleStatus != MoleStatus.Animation) {
            val index = _holes.indexOf(hole)
            if(index != -1) {
                animationJob.cancel()
                _holes[index] = _holes[index].copy(MoleStatus.None)
                _score += SCORE_PER_MOLE
            }
        }
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

    private suspend fun startMoleAnimation(holeIndex: Int, isReverse: Boolean) {
        var frames = framesOfMoleAnimation
        if (isReverse) {
            frames = frames.reversed()
        }
        _holes[holeIndex] = _holes[holeIndex].copy(moleStatus = MoleStatus.Animation)
        for (frame in frames) {
            _currentFrame = frame
            delay(2)
        }
        if(isReverse) {
            _holes[holeIndex] = _holes[holeIndex].copy(moleStatus = MoleStatus.Show)
            _currentFrame = R.drawable.ic_hole_with_mole
        }
        else {
            _holes[holeIndex] = _holes[holeIndex].copy(moleStatus = MoleStatus.None)
            _currentFrame = R.drawable.ic_hole
        }
    }

    private suspend fun startTimer() {
        while(_gameTimer != 0) {
            _gameTimer -= 1
            delay(1000)
        }
    }
}