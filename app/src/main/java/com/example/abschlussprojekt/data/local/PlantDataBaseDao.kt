package com.example.abschlussprojekt.data.local

import androidx.room.Dao
import androidx.room.Query
import com.example.abschlussprojekt.data.model.Plant

@Dao

interface PlantDataBaseDao {
    @Query("SELECT * FROM Plant")
    suspend fun getAllPlants(): List<Plant>
}