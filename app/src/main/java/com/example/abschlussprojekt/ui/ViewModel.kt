package com.example.abschlussprojekt.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.abschlussprojekt.data.Repository
import com.example.abschlussprojekt.remote.PlantApi

class ViewModel(application: Application) :
    AndroidViewModel(application)
val repository = Repository(PlantApi)
val lexiconList = repository.getPlantList