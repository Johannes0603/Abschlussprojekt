package com.example.abschlussprojekt.data.db

import android.content.Context
import com.example.abschlussprojekt.data.dataclass.RecipeData
import com.example.abschlussprojekt.data.exampleData.RecipeExamplesData

class RecipeRepository(private val database: RecipeDatabase) {

    companion object {
        private var Repository: RecipeRepository? = null

        fun getInstance(context: Context): RecipeRepository =
            Repository ?: buildRepo(
                RecipeDatabase.getInstance(context.applicationContext)
            ).also {
                Repository = it
            }

        private fun buildRepo(cookingListDatabase: RecipeDatabase): RecipeRepository =
            RecipeRepository(cookingListDatabase)
    }

    fun prepopulateDB(){
        try{
            if(database.library.getCount() == 0){
                database.library.insertItem(RecipeExamplesData.recipe1)
            }
        } catch (e: Exception) {
            // Fehlerbehandlung bei Bedarf
        }
    }

    fun updateRecipe(updatedRecipe: RecipeData) {
        try {
            database.library.updateRecipe(updatedRecipe)
        } catch (e: Exception) {
            // Fehlerbehandlung bei Bedarf
        }
    }
}