package com.example.abschlussprojekt.data.model

data class PlantIdentificationRequest(
    val query: PlantIdentificationQuery,
    val language: String,
    val preferredReferential: String,
    val switchToProject: String,
    val bestMatch: String
)

data class PlantIdentificationQuery(
    val project: String,
    val images: List<String>,
    val organs: List<String>,
    val includeRelatedImages: Boolean
)