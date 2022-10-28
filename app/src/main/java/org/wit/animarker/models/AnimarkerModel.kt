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
                        //var dateAvailable: LocalDate = LocalDate.now(),
                          var image: Uri = Uri.EMPTY,
                          var lat : Double = 0.0,
                          var lng: Double = 0.0,
                          var zoom: Float = 0f) : Parcelable


@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable