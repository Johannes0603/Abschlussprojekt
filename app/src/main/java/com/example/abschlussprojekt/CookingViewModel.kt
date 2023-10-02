package com.example.abschlussprojekt

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussprojekt.data.dataclass.RecipeData
import com.example.abschlussprojekt.data.exampleData.CookData
import com.example.abschlussprojekt.data.model.cookRecipes

class CookingViewModel (application: Application) :
    AndroidViewModel(application) {

        private val repository = CookData()
    val inputText = MutableLiveData<String>()
    val cookingList = repository.recipe
    private val _currentRecipe = MutableLiveData<RecipeData>()
    val currentRecipe: LiveData<RecipeData>
        get() = _currentRecipe

    private val _allRecipes = MutableLiveData<List<RecipeData>>(cookingList)
    val allRecipes: MutableLiveData<List<RecipeData>>
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
                recipe.title.contains(searchText, ignoreCase = true)
            }
            _allRecipes.value = filteredRecipes
        }
    }
    fun detailCurrentRecipe(recipe: RecipeData){
        _currentRecipe.value = recipe
    }

}