package com.example.abschlussprojekt.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.abschlussprojekt.data.dataclass.RecipeData

@Dao
interface RecipeDao {

    @Insert
    fun insertItem(ItemData: RecipeData)


    @Query("SELECT COUNT(*) FROM cookRecipe_table")
    fun getCount(): Int
}