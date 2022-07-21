package ru.manzharovn.whac_a_mole.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.manzharovn.whac_a_mole.data.repository.ScoreRepository
import ru.manzharovn.whac_a_mole.ui.startmenu.StartMenuViewModel
import javax.inject.Inject

class GameViewModelFactory @Inject constructor(
    val repository: ScoreRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GameViewModel(
            repository = repository
        ) as T
    }

}