package org.wit.animarker.main

import android.app.Application
import org.wit.animarker.models.AnimarkerMemStore
import org.wit.animarker.models.AnimarkerJSONStore
import org.wit.animarker.models.AnimarkerStore
// import org.wit.animarker.models.AnimarkerModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {
    lateinit var animarkers: AnimarkerStore
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        animarkers = AnimarkerJSONStore(applicationContext)
        i("Animarker started")

    }

}