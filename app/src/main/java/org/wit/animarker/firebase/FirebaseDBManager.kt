package org.wit.animarker.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.collection.ArraySortedMap.fromMap
import com.google.firebase.database.ktx.getValue
import org.wit.animarker.models.AnimarkerModel
import org.wit.animarker.models.AnimarkerStore
import timber.log.Timber

object FirebaseDBManager : AnimarkerStore {

    var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    override fun findAll(): List<AnimarkerModel> {
        TODO("Not yet implemented")
    }

    override fun findAll(animarkers: MutableLiveData<List<AnimarkerModel>>) {
        TODO("Not yet implemented")
    }

    override fun findAll(userid: String, animarkers: MutableLiveData<List<AnimarkerModel>>) {
        database.child("user-animarkers").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Animarker error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<AnimarkerModel>()
                    val children = snapshot.children
                    children.forEach {
                        val data = it.value as HashMap<String, Any?>
                        val animarker = AnimarkerModel.fromMap(data!!)
                        localList.add(animarker)
                    }
                    database.child("user-listings").child(userid)
                        .removeEventListener(this)

                    animarkers.value = localList
                }
            })
    }

    override fun create(animarker: AnimarkerModel) {
        TODO("Not yet implemented")
    }

    override fun findById(
        userid: String,
        animarkerid: String,
        animarker: MutableLiveData<AnimarkerModel>
    ) {
        database.child("user-animarkers").child(userid)
            .child(animarkerid).get().addOnSuccessListener {
                animarker.value = it.getValue(AnimarkerModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener {
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, animarker: AnimarkerModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("listings").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        animarker.uid = key
        val animarkerValues = animarker.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/animarkers/$key"] = animarkerValues
        childAdd["/user-animarkers/$uid/$key"] = animarkerValues

        database.updateChildren(childAdd)
    }

    override fun update(animarker: AnimarkerModel) {
        TODO("Not yet implemented")
    }

    override fun delete(userid: String, animarkerid: String) {
        val childDelete: MutableMap<String, Any?> = HashMap()
        childDelete["/animarkers/$animarkerid"] = null
        childDelete["/user-animarkers/$userid/$animarkerid"] = null
        database.updateChildren(childDelete)
    }

    override fun update(userid: String, animarkerid: String, animarker: AnimarkerModel) {
        val animarkerValues = animarker.toMap()
        val childUpdate: MutableMap<String, Any?> = HashMap()
        childUpdate["animarkers/$animarkerid"] = animarkerValues
        childUpdate["user-animarkers/$userid/$animarkerid"] = animarkerValues
        database.updateChildren(childUpdate)
    }

    override fun delete(animarker: AnimarkerModel) {
        TODO("Not yet implemented")
    }

}