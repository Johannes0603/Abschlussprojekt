package com.example.abschlussprojekt

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.abschlussprojekt.data.Repository
import com.example.abschlussprojekt.data.local.PlantDataBase.Companion.getDataBase

import com.example.abschlussprojekt.data.model.Plant
import com.example.abschlussprojekt.remote.PlantApi
import kotlinx.coroutines.launch

class LexiconViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repository = Repository(PlantApi, getDataBase(application))
    val lexiconList: LiveData<List<Plant>> = repository.allPlants

    fun getPlants(term: String) {
        viewModelScope.launch {
            repository.getPlants(term)
        }
    }

}