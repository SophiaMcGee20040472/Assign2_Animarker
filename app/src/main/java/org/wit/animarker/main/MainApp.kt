package org.wit.animarker.main

import android.app.Application
import org.wit.animarker.models.AnimarkerMemStore
// import org.wit.animarker.models.AnimarkerModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val animarkers = AnimarkerMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Animarker started")

    }

}