package com.teammovil.pettracker.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.teammovil.pettracker.data.database.entities.AdopterEntity
import com.teammovil.pettracker.domain.Adopter

@Dao
interface AdopterDao {

    @Insert
    fun addAdopter(adopter: AdopterEntity)

    @Delete
    fun deleteAdopter(adopter: AdopterEntity)

    @Query("SELECT * FROM Adopter")
    fun getAdopter(): AdopterEntity
}