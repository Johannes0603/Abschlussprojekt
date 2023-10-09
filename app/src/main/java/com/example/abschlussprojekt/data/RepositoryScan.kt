package com.example.abschlussprojekt.data

import com.example.abschlussprojekt.data.model.PlantIdentificationQuery
import com.example.abschlussprojekt.remote.ApiScanner
import com.example.abschlussprojekt.data.model.PlantIdentificationRequest
import com.example.abschlussprojekt.data.model.PlantIdentificationResult

class RepositoryScan(private val apiScanner: ApiScanner) {

    suspend fun identifyPlant(
        project: String,
        images: List<String>,
        organs: List<String>,
        includeRelatedImages: Boolean,
        language: String,
        preferredReferential: String,
        switchToProject: String,
        bestMatch: String
    ): PlantIdentificationResult {
        val request = PlantIdentificationRequest(
            PlantIdentificationQuery(project, images, organs, includeRelatedImages),
            language, preferredReferential, switchToProject, bestMatch
        )
        return apiScanner.identifyPlants(request)
    }
}