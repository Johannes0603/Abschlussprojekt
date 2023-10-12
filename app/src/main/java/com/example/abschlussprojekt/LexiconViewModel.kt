package com.example.abschlussprojekt

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.abschlussprojekt.data.Repository
import com.example.abschlussprojekt.data.dbPlants.getDataBase
import com.example.abschlussprojekt.data.model.Plant
import com.example.abschlussprojekt.remote.PlantApi
import kotlinx.coroutines.launch

class LexiconViewModel(application: Application) :
    AndroidViewModel(application) {

    val db = getDataBase(application)

    private val repository = Repository(PlantApi, db)

    val lexiconList = repository.allPlants

    private val _currentPlant = MutableLiveData<Plant>()

    val inputText = MutableLiveData<String>()

    val favPlants = repository.favPlants


    private var currentPage = 1 // Startseite

    val currentPlant: LiveData<Plant>
        get() = _currentPlant
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

    private val _isLiked = MutableLiveData<Boolean>(false)
    val isLiked: LiveData<Boolean> get() = _isLiked
    private val _isDisliked = MutableLiveData<Boolean>(false)
    val isDisliked: LiveData<Boolean> get() = _isDisliked

    fun likePlant() {

        _currentPlant.value?.liked = true
        _currentPlant.value?.dislike = false
        _isLiked.value = true
        _isDisliked.value = false

    }
    fun dislikePlant() {
        _currentPlant.value?.liked = false
        _currentPlant.value?.dislike = true
        _isLiked.value = false
        _isDisliked.value = true

    }
    fun safePlantFav(){
        _currentPlant.value?.let {
            viewModelScope.launch{
                repository.plantToFav(it)
            }
        }
    }
    fun removePlantFav(){
        _currentPlant.value?.let{
            viewModelScope.launch{
                _currentPlant.value?.let { repository.removePlantFav(it.id) }
            }
        }
    }
}