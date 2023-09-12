package com.example.abschlussprojekt.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


@Entity
data class Plant(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @Json(name = "common_name") val commonName: String,
    @Json(name = "scientific_name") val scientificName: String,
    @Json(name = "image_url") val imageUrl: String
)
