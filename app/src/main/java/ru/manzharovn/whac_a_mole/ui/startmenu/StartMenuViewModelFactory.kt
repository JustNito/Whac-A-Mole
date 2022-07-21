package ru.manzharovn.whac_a_mole.ui.startmenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.manzharovn.whac_a_mole.data.repository.ScoreRepository
import javax.inject.Inject

class StartMenuViewModelFactory @Inject constructor(
    val repository: ScoreRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StartMenuViewModel(
            repository = repository
        ) as T
    }

}