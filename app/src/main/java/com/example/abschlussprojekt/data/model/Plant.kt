package com.example.abschlussprojekt.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


@Entity
data class Plant (
   // @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @Json(name = "id") var id: Int,
    @Json(name = "common_name") var common_name: String,
    @Json(name = "default_image") var default_image: String
)
