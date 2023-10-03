package com.example.abschlussprojekt

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussprojekt.data.dataclass.RecipeData
import com.example.abschlussprojekt.data.exampleData.CookData


class CookingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CookData()
    val inputText = MutableLiveData<String>()
    val cookingList = repository.recipes
    private val _currentRecipe = MutableLiveData<RecipeData>()
    val currentRecipe: LiveData<RecipeData>
        get() = _currentRecipe

    val allRecipes: LiveData<List<RecipeData>> = MutableLiveData<List<RecipeData>>(cookingList)

    init {
        inputText.observeForever { searchText ->
            filterRecipes(searchText)
        }
    }

    private fun filterRecipes(searchText: String?) {
        if (searchText.isNullOrBlank()) {
            (allRecipes as MutableLiveData).value = cookingList
        } else {
            val filteredRecipes = cookingList.filter { recipe ->
                recipe.title.contains(searchText, ignoreCase = true)
            }
            (allRecipes as MutableLiveData).value = filteredRecipes
        }
    }

    fun detailCurrentRecipe(recipe: RecipeData) {
        _currentRecipe.value = recipe
    }

    fun updateRecipe(updatedRecipe: RecipeData) {
        repository.updateRecipe(updatedRecipe)
        // Hier aktualisieren wir die Liste der Rezepte direkt in der LiveData
        val updatedList = (allRecipes.value ?: emptyList()).toMutableList()
        val index = updatedList.indexOfFirst { it.id == updatedRecipe.id }
        if (index != -1) {
            updatedList[index] = updatedRecipe
            (allRecipes as MutableLiveData).value = updatedList
        }
    }
}