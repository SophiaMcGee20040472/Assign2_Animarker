package org.wit.animarker.main

import android.app.Application
import org.wit.animarker.models.AnimarkerJSONStore
import org.wit.animarker.models.AnimarkerMemStore
import org.wit.animarker.models.AnimarkerStore
import org.wit.animarker.models.UserJSONStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var animarkers: AnimarkerStore
    lateinit var users: UserJSONStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        animarkers = AnimarkerJSONStore(applicationContext)
        users = UserJSONStore(applicationContext)
        i("Animarker started")
    }
}