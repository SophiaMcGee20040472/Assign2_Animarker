
package org.wit.animarker.models

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import kotlinx.parcelize.Parcelize
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset


@Parcelize
data class AnimarkerModel(
    var id: Long = 0,
    var title: String = "",
    var description: String = "",
    var destination: String = "",
    var date:String="",
    var image: String="",
    var uid: String? = "",
    var lat: Long? = 0,
    var lng: Long? = 0,
    var zoom: Float? = 0f,
    var itemAvailable: Boolean = true,
    var dateAvailable: LocalDate = LocalDate.now(),
    var email: String? = "joe@bloggs.com"
) : Parcelable {


    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "title" to title,
            "description" to description,
            "destination" to destination,
            "date" to date,
            "lat" to lat,
            "lng" to lng,
            "zoom" to zoom,
            "email" to email
        )
    }

    companion object {
        @Exclude
        fun fromMap(map: Map<String, Any?>): AnimarkerModel {
            return AnimarkerModel(
                uid = map["uid"].toString(),
                title = map["title"].toString(),
                destination = map["destination"].toString(),
                description = map["description"].toString(),
                date = map["date"].toString(),
                lat = map["lat"] as? Long,
                lng = map["lng"] as? Long,
                zoom = map["zoom"] as? Float,
                dateAvailable = Instant.ofEpochMilli(map["dateAvailable"] as Long)
                    .atZone(ZoneId.systemDefault()).toLocalDate(),
                email = map["email"].toString()
            )
        }
    }
}






/*
package org.wit.animarker.models

import android.net.Uri
import android.os.Parcelable
import android.widget.RatingBar
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class AnimarkerModel(var id: Long = 0,
                          var title: String = "",
                          var description: String = "",
                          var destination: String = "",
                          var date:String="",
                          var uid:String="",
                          var image: Uri = Uri.EMPTY,
                          var lat : Double = 0.0,
                          var lng: Double = 0.0,
                          var zoom: Float = 0f) : Parcelable {
}


@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable
*/
