package ru.manzharovn.whac_a_mole.data.repository

import ru.manzharovn.whac_a_mole.data.storage.LocalStorage
import javax.inject.Inject

class ScoreRepository @Inject constructor(val localStorage: LocalStorage) {

    fun getHighScore(): Int = localStorage.getScore()

    fun setHighScore(score: Int) = localStorage.setScore(score)
}