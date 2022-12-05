package org.wit.animarker.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Welcome to Animarker. I decided to make an app for nature lovers that can upload pictures of animals they spot all over the world. I have included Species Name, Description, Destination and Date in which the animal is spotted. Add your animal spotted to the map and it gives you the coordinates where it was spotted.This could be a great app for lost and stray animals that are spotted so owners can track their pet that might be lost in the area."
    }
    val text: LiveData<String> = _text
}