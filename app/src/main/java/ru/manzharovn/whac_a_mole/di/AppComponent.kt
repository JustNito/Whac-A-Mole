package ru.manzharovn.whac_a_mole.di

import android.content.Context
import android.provider.ContactsContract
import dagger.BindsInstance
import dagger.Component
import ru.manzharovn.whac_a_mole.MainActivity

@Component(modules = [DataModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
}