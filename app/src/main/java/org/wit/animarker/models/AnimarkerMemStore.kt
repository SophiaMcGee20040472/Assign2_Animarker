package org.wit.animarker.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class AnimarkerMemStore : AnimarkerStore {

    val animarkers = ArrayList<AnimarkerModel>()

    override fun findAll(): List<AnimarkerModel> {
        return animarkers
    }

    override fun create(animarker: AnimarkerModel) {
        animarker.id = getId()
        animarkers.add(animarker)
        logAll()
    }

    override fun update(animarker: AnimarkerModel) {
        val foundAnimarker: AnimarkerModel? = animarkers.find { p -> p.id == animarker.id }
        if (foundAnimarker != null) {
            foundAnimarker.title = animarker.title
            foundAnimarker.description = animarker.description
            logAll()
        }
    }
    override fun delete(animarker: AnimarkerModel) {
        animarkers.remove(animarker)
        logAll()
    }
    fun logAll() {
        animarkers.forEach{ i("$it")}
    }
}