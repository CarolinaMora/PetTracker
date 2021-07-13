package com.teammovil.pettracker.data.database

import com.teammovil.domain.Adopter
import com.teammovil.domain.Rescuer
import com.teammovil.pettracker.data.database.entities.AdopterEntity
import com.teammovil.pettracker.data.database.entities.RescuerEntity
import java.util.*

object Mapper {

    fun map(adopterEntity: AdopterEntity): Adopter {
        return Adopter(
            adopterEntity.email,
            adopterEntity.name,
            adopterEntity.firstLastName,
            adopterEntity.secondLastName,
            adopterEntity.gender,
            adopterEntity.BirthDate,
            adopterEntity.password,
            adopterEntity.phone,
            adopterEntity.address
        )
    }

    fun adopterEntityMap(adopter: Adopter): AdopterEntity{
        return AdopterEntity(
            adopter.email,
            adopter.name,
            adopter.firstLastName,
            adopter.secondLastName,
            adopter.gender,
            adopter.BirthDate?:Date(),
            adopter.password,
            adopter.phone,
            adopter.address
        )
    }

    fun map(rescuerEntity: RescuerEntity) = Rescuer(
        rescuerEntity.id,
        rescuerEntity.name,
        rescuerEntity.description,
        rescuerEntity.address,
        rescuerEntity.email,
        rescuerEntity.password,
        rescuerEntity.phone,
        rescuerEntity.activityStartDate
    )

    fun map(rescuer: Rescuer) = RescuerEntity (
        rescuer.id,
        rescuer.name,
        rescuer.description,
        rescuer.address,
        rescuer.email,
        rescuer.password,
        rescuer.phone,
        rescuer.activityStartDate ?: Date()
    )

}