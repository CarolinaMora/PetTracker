package com.teammovil.pettracker.data.database

import com.teammovil.pettracker.data.database.entities.AdopterEntity
import com.teammovil.pettracker.data.database.entities.RescuerEntity
import com.teammovil.domain.Adopter
import com.teammovil.domain.Rescuer

object Mapper {

    fun map(adopterEntity: AdopterEntity): com.teammovil.domain.Adopter {
        return com.teammovil.domain.Adopter(
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

    fun adopterEntityMap(adopter: com.teammovil.domain.Adopter): AdopterEntity{
        return AdopterEntity(
            adopter.email,
            adopter.name,
            adopter.firstLastName,
            adopter.secondLastName,
            adopter.gender,
            adopter.BirthDate,
            adopter.password,
            adopter.phone,
            adopter.address
        )
    }

    fun map(rescuerEntity: RescuerEntity) = com.teammovil.domain.Rescuer(
        rescuerEntity.id,
        rescuerEntity.name,
        rescuerEntity.description,
        rescuerEntity.address,
        rescuerEntity.email,
        rescuerEntity.password,
        rescuerEntity.phone,
        rescuerEntity.activityStartDate
    )

    fun map(rescuer: com.teammovil.domain.Rescuer) = RescuerEntity (
        rescuer.id,
        rescuer.name,
        rescuer.description,
        rescuer.address,
        rescuer.email,
        rescuer.password,
        rescuer.phone,
        rescuer.activityStartDate
    )

}