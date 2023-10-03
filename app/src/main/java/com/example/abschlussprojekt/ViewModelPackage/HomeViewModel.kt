package com.example.abschlussprojekt.ViewModelPackage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.abschlussprojekt.data.Repository
import com.example.abschlussprojekt.data.dbPlants.PlantDataBase.Companion.getDataBase
import com.example.abschlussprojekt.remote.PlantApi
import kotlinx.coroutines.launch

class HomeViewModel (application: Application) :
    AndroidViewModel(application) {

    val db = getDataBase(application)
    val repository = Repository(PlantApi,db)

    fun getResult(term: String){
        viewModelScope.launch {
            repository.getPlantsSearch(term)
        }
    }
}