package com.example.abschlussprojekt.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


@Entity
data class Plant(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @Json(name = "common_name") var commonName: String?,
    @Json(name = "scientific_name") var scientificName: String?,
    @Json(name = "image_url") var imageUrl: String?,
    //@Json(name = "links") val links: String,
    @Json(name = "next") var next : String?,
    @Json(name = "prev") var prev : String?
){
    var liked: Boolean = false
    var dislike: Boolean = false

}
