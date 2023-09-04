package com.example.abschlussprojekt.data.model

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity
data class scanner (
    // @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @Json(name = "project") var project: String,
    @Json(name = "lang") var lang: String,
    @Json(name = "api-key") var api_key: String
){
    var id : String = ""
    var scientificNameWithoutAuthor: String = ""
    var scientificNameAuthorship : String = ""
    var gbifId : Int = 0
    var powoId : String = ""
    var iucnCategory : String = ""
    var commonNames: String = ""

}
