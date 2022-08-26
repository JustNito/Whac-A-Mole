package ru.manzharovn.whac_a_mole.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.manzharovn.whac_a_mole.data.repository.ScoreRepository
import ru.manzharovn.whac_a_mole.ui.game.GameViewModel
import ru.manzharovn.whac_a_mole.ui.resultscore.ResultViewModel
import ru.manzharovn.whac_a_mole.ui.startmenu.StartMenuViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    val repository: ScoreRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(StartMenuViewModel::class.java) -> {
                StartMenuViewModel(repository)
            }
            isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(repository)
            }
            isAssignableFrom(GameViewModel::class.java) -> {
                GameViewModel(repository)
            } else ->
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}