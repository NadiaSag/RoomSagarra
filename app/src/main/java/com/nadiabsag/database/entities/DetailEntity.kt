package com.nadiabsag.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nadiabsag.room.SuperheroDetail

@Entity(tableName = "detail_table")

data class DetailEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "intelligence") val intelligence: String,
    @ColumnInfo(name = "strength") val strength: String,
    @ColumnInfo(name = "speed") val speed: String,
    @ColumnInfo(name = "durability") val durability: String,
    @ColumnInfo(name = "power") val power: String,
    @ColumnInfo(name = "combat") val combat: String,
    @ColumnInfo(name = "full-name") val fullName: String,
    @ColumnInfo(name = "publisher") val publisher:String
)
fun SuperheroDetail.toDatabase() = DetailEntity(
    intelligence = powerstats.intelligence,
    strength = powerstats.strength,
    speed = powerstats.speed,
    durability = powerstats.durability,
    power = powerstats.power,
    combat = powerstats.combat,
    fullName = biography.fullName,
    publisher = biography.publisher)



