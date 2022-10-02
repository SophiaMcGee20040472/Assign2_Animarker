package org.wit.animarker.models

interface AnimarkerStore {
    fun findAll(): List<AnimarkerModel>
    fun create(animarker: AnimarkerModel)
    fun update(animarker: AnimarkerModel)
}