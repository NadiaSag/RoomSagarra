// DatabaseProvider.kt
package com.nadiabsag.room

import android.content.Context
import androidx.room.Room
import com.nadiabsag.database.SuperheroDatabase

object DatabaseProvider {
    private var database: SuperheroDatabase? = null

    fun getDatabase(context: Context): SuperheroDatabase {
        return database ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                SuperheroDatabase::class.java,
                "superheroes_database"
            ).build()
            database = instance
            instance
        }
    }
}
