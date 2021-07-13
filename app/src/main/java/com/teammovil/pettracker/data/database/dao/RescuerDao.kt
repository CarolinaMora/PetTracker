package com.teammovil.pettracker.data.database.dao

import androidx.room.*
import com.teammovil.pettracker.data.database.entities.RescuerEntity
@Dao
interface RescuerDao {

    @Query("SELECT * FROM Rescuer")
    suspend fun getRescuer(): RescuerEntity

    @Insert
    suspend fun addRescuer(rescuer: RescuerEntity)

    @Query("DELETE FROM Rescuer")
    suspend fun deleteRescuer()

    @Update
    suspend fun updateRescuer(rescuer: RescuerEntity)

}