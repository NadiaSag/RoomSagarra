package com.nadiabsag.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nadiabsag.database.dao.DetailDao
import com.nadiabsag.database.dao.ListDao
import com.nadiabsag.database.entities.DetailEntity
import com.nadiabsag.database.entities.ListEntity

@Database(entities = [ListEntity::class, DetailEntity::class], version = 17)
abstract class SuperheroDatabase : RoomDatabase() {
    abstract fun getListDao(): ListDao
    abstract fun getDetailDao(): DetailDao
}
