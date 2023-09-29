package com.example.abschlussprojekt.data.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cookRecipe_table")

data class RecipeData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var info: String
)