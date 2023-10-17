package com.example.abschlussprojekt.data.dbPlants

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.abschlussprojekt.data.model.Plant

@Database(entities = [Plant::class], version = 1)
abstract class PlantDataBase : RoomDatabase() {
    abstract val PlantDataBaseDao: PlantDataBaseDao
}

private lateinit var INSTANCE: PlantDataBase

fun getDataBase(context: Context): PlantDataBase{
    synchronized(PlantDataBase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                PlantDataBase::class.java,
                "plant_database"
            )
                .build()
        }
    }
    return INSTANCE
}