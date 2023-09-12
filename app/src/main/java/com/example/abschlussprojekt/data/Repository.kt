package com.example.abschlussprojekt.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.abschlussprojekt.data.local.PlantDataBase
import com.example.abschlussprojekt.data.model.Plant
import com.example.abschlussprojekt.remote.PlantApi
import kotlinx.coroutines.runBlocking

const val TAG = "AppRepositoryTAG"

class Repository(private val api: PlantApi, private val dataBase: PlantDataBase) {
    private val _plantList = MutableLiveData<List<Plant>>()

    val allPlants: LiveData<List<Plant>> = liveData {
        emit(dataBase.PlantDataBaseDao.getAllPlants())
    }

    val getPlantList: LiveData<List<Plant>>
        get() = _plantList

    suspend fun getPlants(term: String) {
        val results = api.retrofitService.getPlant("").results
        _plantList.value = results.sortedBy { it.commonName }
    }
}


