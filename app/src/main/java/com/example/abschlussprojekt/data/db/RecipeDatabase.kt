package com.example.abschlussprojekt.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.abschlussprojekt.data.model.RecipeData

@Database(entities = [RecipeData::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {

    /** Variable für das Interface aus der RecipeDao */
    abstract val library: RecipeDao

    companion object{
        /** Speichert die Instance der BookDatabase um mit dieser arbeiten zu können */
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        /**
         * Liefert die Instance der BookDatabase zurück
         *
         * @param context Kontext von welchem der Aufruf kam
         *
         * @return BookDatabase Context
         */

        fun getInstance(context: Context): RecipeDatabase{
            synchronized(this){
                var instance = INSTANCE
                // Beim ersten mal, wenn noch keine eine Instanz besteht muss diese erstellt werden
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecipeDatabase::class.java,
                        "recipe_database"
                    ).allowMainThreadQueries().build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}