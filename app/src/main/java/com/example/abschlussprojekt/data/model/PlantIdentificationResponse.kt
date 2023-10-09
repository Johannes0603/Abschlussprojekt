package com.example.abschlussprojekt.data.model

import com.squareup.moshi.Json

data class PlantIdentificationResponse(
    @Json(name = "id") val id: String,
    @Json(name = "common_name") val commonName: String?,
    @Json(name = "slug") val slug: String?,
    @Json(name = "scientific_name") val scientificName: String?,
    @Json(name = "year") val year: Int?,
    @Json(name = "bibliography") val bibliography: String?,
    @Json(name = "author") val author: String?,
    @Json(name = "status") val status: String?,
    @Json(name = "rank") val rank: String?,
    @Json(name = "family_common_name") val familyCommonName: String?,
    @Json(name = "genus_id") val genusId: Int?,
    @Json(name = "image_url") val imageUrl: String?,
    @Json(name = "genus") val genus: String?,
    @Json(name = "family") val family: String?,
    @Json(name = "links") val links: Links?
)

data class Links(
    @Json(name = "self") val self: String?,
    @Json(name = "plant") val plant: String?
)
