package com.example.abschlussprojekt.ViewModelPackage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussprojekt.data.RepositoryScan
import com.example.abschlussprojekt.data.model.PlantIdentificationResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlantIdentificationVM(application: Application, private val repository: RepositoryScan) :
    AndroidViewModel(application) {

    private val _plantIdentificationResult = MutableLiveData<PlantIdentificationResult>()
    val plantIdentificationResult: LiveData<PlantIdentificationResult>
        get() = _plantIdentificationResult

    fun identifyPlant(
        project: String,
        images: List<String>,
        organs: List<String>,
        includeRelatedImages: Boolean,
        language: String,
        preferredReferential: String,
        switchToProject: String,
        bestMatch: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.identifyPlant(
                project, images, organs, includeRelatedImages,
                language, preferredReferential, switchToProject, bestMatch
            )
            _plantIdentificationResult.postValue(result)
        }
    }
}