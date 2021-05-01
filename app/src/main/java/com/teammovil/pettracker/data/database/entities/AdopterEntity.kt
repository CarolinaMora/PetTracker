package com.teammovil.pettracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.teammovil.pettracker.domain.GenderType
import java.util.*

@Entity(tableName = "Adopter")
class AdopterEntity(
    @PrimaryKey
    var email: String = "",
    var name: String = "",
    var firstLastName: String = "",
    var secondLastName: String = "",
    var gender: GenderType = GenderType.MALE,
    var BirthDate: Date = Date(),
    var password: String = "",
    var phone: String = "",
    var address: String = ""
)