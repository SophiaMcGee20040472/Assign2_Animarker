package org.wit.animarker.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.animarker.models.AnimarkerManager
import org.wit.animarker.models.AnimarkerModel

class ListViewModel : ViewModel() {

    private val animarkers = MutableLiveData<List<AnimarkerModel>>()
    val observableAnimarkers: LiveData<List<AnimarkerModel>>
        get() = animarkers

   init {
       load()
   }

    fun load() {
        animarkers.value = AnimarkerManager.findAll()
    }
}