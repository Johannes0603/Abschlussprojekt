package com.example.abschlussprojekt.data.db

import android.content.Context
import com.example.abschlussprojekt.data.exampleData.RecipeExamplesData

class RecipeRepository(private val database: RecipeDatabase) {
    /* -------------------- Objekte -------------------- */

    /**
     * Companion Object, welches innerhalb der Klasse definiert wird und Zugriff auf private Elemente der Klasse hat
     */
    companion object {
        private var Repository: RecipeRepository? = null

        /**
         * Funktion um eine Instanz des Repositories zu erhalten
         *
         * @param    context        Der Context des aufrufenden ViewModels
         */
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
        }catch (e: Exception){}
    }
}