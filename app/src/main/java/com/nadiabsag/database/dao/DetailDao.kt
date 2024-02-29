package com.nadiabsag.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nadiabsag.database.entities.DetailEntity
import com.nadiabsag.database.entities.ListEntity

@Dao
interface DetailDao {
    @Query("SELECT * FROM detail_table")
    suspend fun getAllSuperheroDetail():List<DetailEntity>

    @Query("SELECT * FROM detail_table WHERE publisher LIKE :publisher")
    suspend fun getSuperheroDetailPublisher(publisher:String):List<DetailEntity>

    @Query("SELECT * FROM detail_table WHERE `full-name` LIKE :fullName")
    suspend fun getSuperheroDetailFullName(fullName:String):List<DetailEntity>
    @Query("SELECT id, intelligence, strength, speed, durability, power, combat, `full-name`, publisher FROM detail_table WHERE id LIKE :id")
    suspend fun getSuperhero(id: String): DetailEntity

    @Query("SELECT * FROM detail_table WHERE id LIKE :id")
    suspend fun getSuperheroDetailByID(id:Int):List<DetailEntity>

    @Query("SELECT image FROM list_table WHERE id LIKE :id")
    suspend fun getImage(id:Int): String

    @Query("SELECT name FROM list_table WHERE id LIKE :id")
    suspend fun getSuperheroName(id:Int): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll (superheroesDetail:List<DetailEntity>)

    @Query("DELETE FROM detail_table")
    suspend fun deleteAllSuperheroesDetail()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(superhero: DetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(superhero:DetailEntity)
}