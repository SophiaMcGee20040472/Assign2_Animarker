package org.wit.animarker.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.animarker.models.AnimarkerManager
import org.wit.animarker.models.AnimarkerModel
import timber.log.Timber

class AddViewModel : ViewModel() {
    private val status = MutableLiveData<Boolean>()
    val observableStatus: LiveData<Boolean>
        get() = status

    fun addAnimarker(animarker: AnimarkerModel) {
        status.value = try {
            AnimarkerManager.create(animarker)
            true
        } catch (e: IllegalArgumentException) {
            Timber.i("LOG false :%v", e)
            false
        }
    }
}