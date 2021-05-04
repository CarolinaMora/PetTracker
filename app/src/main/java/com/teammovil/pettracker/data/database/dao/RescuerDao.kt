package com.teammovil.pettracker.data.database.dao

import androidx.room.*
import com.teammovil.pettracker.data.database.entities.RescuerEntity
@Dao
interface RescuerDao {

    @Query("SELECT * FROM Rescuer")
    fun getRescuer(): RescuerEntity

    @Insert
    fun addRescuer(rescuer: RescuerEntity)

    @Query("DELETE FROM Rescuer")
    fun deleteRescuer()

    @Update
    fun updateRescuer(rescuer: RescuerEntity)

}