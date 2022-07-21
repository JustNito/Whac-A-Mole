package ru.manzharovn.whac_a_mole

import android.app.Application
import ru.manzharovn.whac_a_mole.di.DaggerAppComponent

class MyApp : Application() {
    val appComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}