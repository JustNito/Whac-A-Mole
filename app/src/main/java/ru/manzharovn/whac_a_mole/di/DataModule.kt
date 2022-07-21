package ru.manzharovn.whac_a_mole.di

import dagger.Binds
import dagger.Module
import ru.manzharovn.whac_a_mole.data.storage.LocalStorage
import ru.manzharovn.whac_a_mole.data.storage.Prefs

@Module
abstract class DataModule {

    @Binds
    abstract fun provideLocalStorage(localStorage: Prefs): LocalStorage
}