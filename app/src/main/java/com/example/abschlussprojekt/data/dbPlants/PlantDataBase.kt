package com.example.abschlussprojekt.data.dbPlants

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.abschlussprojekt.data.model.Plant

@Database(entities = [Plant::class], version = 3)
abstract class PlantDataBase : RoomDatabase() {
    abstract val PlantDataBaseDao: PlantDataBaseDao

    companion object {
        @Volatile
        private var INSTANCE: PlantDataBase? = null

        fun getDataBase(context: Context): PlantDataBase {
            return INSTANCE ?: synchronized(this) {
                val migration1to2 = object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        // Führe hier die erforderlichen Änderungen am Datenbankschema durch
                    }
                }

                val migration2to3: Migration = object : Migration(2, 3) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        // Führe hier die erforderlichen Änderungen am Datenbankschema durch
                    }
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantDataBase::class.java,
                    "plant_database"
                )
                    .addMigrations(migration1to2, migration2to3)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}