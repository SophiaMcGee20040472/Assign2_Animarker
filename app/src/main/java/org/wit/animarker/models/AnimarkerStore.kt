package org.wit.animarker.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface AnimarkerStore {
    fun findAll(): List<AnimarkerModel>
    fun create(animarker: AnimarkerModel)
    fun update(animarker: AnimarkerModel)
    fun delete(animarker: AnimarkerModel)
    fun findAll(animarkersList: MutableLiveData<List<AnimarkerModel>>)
    fun findAll(userid: String, animarkersList: MutableLiveData<List<AnimarkerModel>>)
    fun findById(userid: String, animarkerid: String, animarker: MutableLiveData<AnimarkerModel>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, animarker: AnimarkerModel)
    fun delete(userid: String, animarkerid: String)
    fun update(userid: String, animarkerid: String, animarker: AnimarkerModel)
}