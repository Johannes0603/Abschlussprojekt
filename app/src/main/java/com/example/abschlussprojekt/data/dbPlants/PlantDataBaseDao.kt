package com.example.abschlussprojekt.data.dbPlants

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.abschlussprojekt.data.model.Plant

@Dao

interface PlantDataBaseDao {
    @Insert
    suspend fun insert(result: Plant)
    @Update
    suspend fun update(result: Plant)
    @Query("SELECT * FROM Plant")
    suspend fun getAllPlants(): List<Plant>

    @Query("DELETE FROM Plant WHERE id = :key")
    suspend fun deleteByID(key: Long)
}