package com.example.abschlussprojekt.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussprojekt.BuildConfig
import com.example.abschlussprojekt.data.dbPlants.PlantDataBase
import com.example.abschlussprojekt.data.model.Plant
import com.example.abschlussprojekt.remote.PlantApi

const val TAG = "AppRepositoryTAG"

class Repository(private val api: PlantApi, private val dataBase: PlantDataBase) {
    private val _plantList = MutableLiveData<List<Plant>>()

    val allPlants: LiveData<List<Plant>>
        get() = _plantList

    val getPlantList: LiveData<List<Plant>>
        get() = _plantList

    suspend fun getPlants(term: String, page: Int) {
        val results = api.retrofitService.getPlants(page,BuildConfig.apiKey,term).data
        _plantList.value = results.sortedBy { it.commonName }
        Log.d("api fail","$results")
    }
    suspend fun getPlantsSearch(term: String) {
        try {
            val results = api.retrofitService.getPlantsSearch(BuildConfig.apiKey,term).data
            _plantList.value = results
        } catch (e: Exception) {
            Log.e("APP", "ERROR,LOADING FAILED : $e")
        }
    }
}


