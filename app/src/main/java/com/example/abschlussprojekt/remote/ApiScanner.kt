package com.example.abschlussprojekt.remote

import com.example.abschlussprojekt.data.model.PlantIdentificationRequest
import com.example.abschlussprojekt.data.model.PlantIdentificationResult
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


const val BASE_URL2 = "https://my-api.plantnet.org/v2/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl(BASE_URL2)
    .build()

interface ApiScanner {
    @GET("identify/all")
    suspend fun identifyPlants(
        @Query("include-related-images") includeRelatedImages: Boolean = false,
        @Query("switchToProject") switchToProject: String,
        @Query("bestMatch") bestMatch: String,
        @Query("lang") lang: String = "en",
        @Query("api-key") apiKey: String
    ): PlantIdentificationResult

    @POST("identify")
    suspend fun identifyPlants(@Body request: PlantIdentificationRequest): PlantIdentificationResult
}



object ApiScannerApi {
    val retrofitService: ApiScanner by lazy { retrofit.create(ApiScanner::class.java) }
}