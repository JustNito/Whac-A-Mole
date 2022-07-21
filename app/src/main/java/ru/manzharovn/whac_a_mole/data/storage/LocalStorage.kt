package ru.manzharovn.whac_a_mole.data.storage

import android.content.Context
import ru.manzharovn.whac_a_mole.R

interface LocalStorage {

    fun getScore(): Int

    fun setScore(score: Int)
}