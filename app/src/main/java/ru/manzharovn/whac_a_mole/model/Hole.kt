package ru.manzharovn.whac_a_mole.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.manzharovn.whac_a_mole.R
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

data class Hole(val moleStatus: MoleStatus)

enum class MoleStatus {
    None,Show,Whacked,Animation
}