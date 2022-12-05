package org.wit.animarker.main

import android.app.Application
import org.wit.animarker.models.AnimarkerManager
import org.wit.animarker.models.AnimarkerStore

class MainApp : Application() {
    lateinit var animarkerStore: AnimarkerStore

    override fun onCreate() {
        super.onCreate()
        //animarkerStore = AnimarkerManager
    }
}