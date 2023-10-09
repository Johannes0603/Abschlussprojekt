package com.example.abschlussprojekt.data.model

import com.squareup.moshi.Json
data class PlantIdentificationResult(
    val results: List<PlantIdentificationResultItem>,
    val remainingIdentificationRequests: Int,
    val version: String
)

data class PlantIdentificationResultItem(
    val score: Int,
    val species: Species,
    val images: List<Image>,
    val gbif: Gbif,
    val powo: Powo,
    val iucn: Iucn
)

data class Species(
    val scientificNameWithoutAuthor: String,
    val scientificNameAuthorship: String,
    val scientificName: String,
    val genus: Genus,
    val family: Family,
    val commonNames: List<String>
)

data class Genus(
    val scientificNameWithoutAuthor: String,
    val scientificNameAuthorship: String,
    val scientificName: String
)

data class Family(
    val scientificNameWithoutAuthor: String,
    val scientificNameAuthorship: String,
    val scientificName: String
)

data class Image(
    val organ: String,
    val author: String,
    val license: String,
    val date: Date,
    val citation: String,
    val url: Url
)

data class Date(
    val timestamp: Long,
    val string: String
)

data class Url(
    val o: String,
    val m: String,
    val s: String
)

data class Gbif(
    val id: Int
)

data class Powo(
    val id: String
)

data class Iucn(
    val id: String,
    val category: String
)