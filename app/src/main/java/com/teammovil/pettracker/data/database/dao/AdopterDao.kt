package com.teammovil.pettracker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.teammovil.pettracker.data.database.entities.AdopterEntity
import com.teammovil.domain.Adopter

@Dao
interface AdopterDao {

    @Insert
    suspend fun addAdopter(adopter: AdopterEntity)

    @Query("DELETE FROM Adopter")
    suspend fun deleteAdopter()

    @Query("SELECT * FROM Adopter")
    suspend fun getAdopter(): AdopterEntity
}