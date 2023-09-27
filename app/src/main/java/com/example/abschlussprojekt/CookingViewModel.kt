package com.example.abschlussprojekt

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussprojekt.data.CookData
import com.example.abschlussprojekt.data.model.cookRecipes

class CookingViewModel (application: Application) :
    AndroidViewModel(application) {

        private val repository = CookData()
    val inputText = MutableLiveData<String>()
    val cookingList = repository.recipe

    private val _allRecipes = MutableLiveData<List<cookRecipes>>(cookingList)
    val allRecipes: MutableLiveData<List<cookRecipes>>
        get()= _allRecipes
    init {
        inputText.observeForever { searchText ->
            filterRecipes(searchText)
        }
    }

    private fun filterRecipes(searchText: String?) {
        if (searchText.isNullOrBlank()) {
            _allRecipes.value = cookingList
        } else {
            val filteredRecipes = cookingList.filter { recipe ->
                recipe.name.contains(searchText, ignoreCase = true)
            }
            _allRecipes.value = filteredRecipes
        }
    }

}