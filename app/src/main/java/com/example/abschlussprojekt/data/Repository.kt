package com.example.abschlussprojekt.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussprojekt.data.model.Plant
import com.example.abschlussprojekt.remote.PlantApi

const val TAG = "AppRepositoryTAG"

class Repository(private val api : PlantApi){
    private val _plantList = MutableLiveData<List<Plant>>()


    val plantList : LiveData<List<Plant>>
        get() = _plantList
    suspend fun getPlants(term: String){
            val results = api.retrofitService.getPlant(term).results
            _plantList.value = results }




}

