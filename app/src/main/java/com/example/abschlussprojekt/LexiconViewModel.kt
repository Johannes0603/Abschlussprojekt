package com.example.abschlussprojekt

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.abschlussprojekt.data.Repository
import com.example.abschlussprojekt.data.local.PlantDataBase.Companion.getDataBase

import com.example.abschlussprojekt.data.model.Plant
import com.example.abschlussprojekt.remote.PlantApi
import kotlinx.coroutines.launch

class LexiconViewModel(application: Application) :
    AndroidViewModel(application) {

    private val _selectedPlant = MutableLiveData<Plant>()
    private val _currentPlant = MutableLiveData<Plant>()
    val inputText = MutableLiveData<String>()
    val currentPlant: LiveData<Plant>
        get() = _currentPlant
    val selectedPlant: LiveData<Plant>
        get() = _selectedPlant

    private val repository = Repository(PlantApi, getDataBase(application))
    val lexiconList = repository.allPlants


    private var currentPage = 1 // Startseite



    fun getPlants(term: String) {
        viewModelScope.launch {
            // Pflanzen für die aktuelle Seite abrufen
            repository.getPlants(term, currentPage)
        }
    }
    fun detailCurrentPlant(plant: Plant){
        _currentPlant.value = plant
    }

    // Methode zum Laden der nächsten Seite
    fun loadNextPage(term: String) {
        currentPage++
        getPlants(term)
    }

    fun getResult(term: String){
        viewModelScope.launch {
            repository.getPlantsSearch(term)
        }
    }

}