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


        /**
         * Lokale Funktion um ein Repository zu erstellen
         *
         * @param shoppingListDatabase    Die Datenbank mit der das Repo verknüpft werden soll
         *
         * @return Ein Repository
         */
        private fun buildRepo(cookingListDatabase: RecipeDatabase): RecipeRepository =
            RecipeRepository(cookingListDatabase)
    }
    /**
     * Funktion um Beispielwerte und Daten von der API bei leerer Datenbank in diese einzufügen
     */
    fun prepopulateDB(){
        try{
            if(database.library.getCount() == 0){
                database.library.insertItem(RecipeExamplesData.recipe1)
            }
        }catch (e: Exception){}
    }
}