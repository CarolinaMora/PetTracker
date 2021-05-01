package com.teammovil.pettracker.data.database

import com.teammovil.pettracker.data.database.entities.AdopterEntity
import com.teammovil.pettracker.domain.Adopter

object Mapper {

    fun map(adopterEntity: AdopterEntity): Adopter{
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
            adopter.BirthDate,
            adopter.password,
            adopter.phone,
            adopter.address
        )
    }
}