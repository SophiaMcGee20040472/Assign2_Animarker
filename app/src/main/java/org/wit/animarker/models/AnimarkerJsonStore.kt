package org.wit.animarker.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.animarker.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "animarkers.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<AnimarkerModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class AnimarkerJSONStore(private val context: Context) : AnimarkerStore {

    var animarkers = mutableListOf<AnimarkerModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<AnimarkerModel> {
        logAll()
        return animarkers
    }

    override fun create(animarker: AnimarkerModel) {
        animarker.id = generateRandomId()
        animarkers.add(animarker)
        serialize()
    }

    override fun update(animarker: AnimarkerModel) {
        var foundAnimarker: AnimarkerModel? = animarkers.find{ l -> l.id == animarker.id}
        if (foundAnimarker != null) {
            foundAnimarker.title = animarker.title
            foundAnimarker.destination = animarker.destination
            foundAnimarker.description = animarker.description
            //foundAnimarker.dateAvailable = animarker.dateAvailable
            foundAnimarker.image = animarker.image
            logAll()
        }
        serialize()
    }

    override fun delete(animarker: AnimarkerModel) {
        animarkers.remove(animarker)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(animarkers, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        animarkers = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        animarkers.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}