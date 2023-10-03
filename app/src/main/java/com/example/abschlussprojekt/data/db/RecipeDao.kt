package com.example.abschlussprojekt.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.abschlussprojekt.data.dataclass.RecipeData

@Dao
interface RecipeDao {

    @Insert
    fun insertItem(itemData: RecipeData)

    @Update
    fun updateRecipe(recipe: RecipeData)

    @Query("SELECT COUNT(*) FROM cookRecipe_table")
    fun getCount(): Int
}