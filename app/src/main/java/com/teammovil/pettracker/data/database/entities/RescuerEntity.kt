package com.teammovil.pettracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity (tableName = "Rescuer")
class RescuerEntity (
        @PrimaryKey
        val id: String,
        val name: String,
        val description: String,
        val address: String?,
        val email: String,
        val password: String,
        val phone: String,
        val activityStartDate: Date
        )
