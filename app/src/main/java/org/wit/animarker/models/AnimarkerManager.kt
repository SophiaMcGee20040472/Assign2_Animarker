package org.wit.animarker.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object AnimarkerManager : AnimarkerStore {

    private val animarkers = ArrayList<AnimarkerModel>()

    override fun findAll(): List<AnimarkerModel> {
        return animarkers
    }

    override fun findAll(animarkersList: MutableLiveData<List<AnimarkerModel>>) {
        TODO("Not yet implemented")
    }

    override fun findAll(userid: String, animarkersList: MutableLiveData<List<AnimarkerModel>>) {
        TODO("Not yet implemented")
    }

    override fun create(animarker: AnimarkerModel) {
        animarker.id = getId()
        animarkers.add(animarker)
        logAll()
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, animarker: AnimarkerModel) {
        TODO("Not yet implemented")
    }

    override fun update(animarker: AnimarkerModel) {
        var foundAnimarker: AnimarkerModel? = animarkers.find { l -> l.id == animarker.id }
        if (foundAnimarker != null) {
            foundAnimarker.title = animarker.title
            foundAnimarker.destination = animarker.destination
            foundAnimarker.description = animarker.description
            foundAnimarker.date = animarker.date
            foundAnimarker.image = animarker.image
            foundAnimarker.lat = animarker.lat
            foundAnimarker.lng = animarker.lng
            foundAnimarker.zoom = animarker.zoom
            logAll()
        }
    }

    override fun update(userid: String, animarkerid: String, animarker: AnimarkerModel) {
        TODO("Not yet implemented")
    }

    override fun delete(animarker: AnimarkerModel) {
        animarkers.remove(animarker)
        logAll()
    }

    override fun delete(userid: String, animarkerid: String) {
    }

    override fun findById(
        userid: String,
        animarkerid: String,
        animarker: MutableLiveData<AnimarkerModel>
    ) {
        TODO("Not yet implemented")
    }

    fun logAll() {
        animarkers.forEach { Timber.i("${it}") }
    }
}

