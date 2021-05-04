package com.teammovil.pettracker.data.fakes

import com.teammovil.pettracker.getDateFromString
import java.util.*

val fakeAdopterList: MutableList<com.teammovil.domain.Adopter> = listOf(
    com.teammovil.domain.Adopter(
        "example@gmail.com",
        "María",
        "Cardenaz",
        "Gonzalez",
        com.teammovil.domain.GenderType.FEMALE,
        getDateFromString("2020-09-01")?.let { it } ?: Date(),
        "password123",
        "7471879475",
        "Eje 23 Sur 39, Ciudad de México"
    ),
    com.teammovil.domain.Adopter(
        "example@gmail.com",
        "Alberto",
        "Martínez",
        "Robledo",
        com.teammovil.domain.GenderType.MALE,
        getDateFromString("2020-09-01")?.let { it } ?: Date(),
        "password123",
        "747187947389",
        "Eje 8 Sur 84, Ciudad de México"
    ),
    com.teammovil.domain.Adopter(
        "example@gmail.com",
        "Adrian",
        "Guzman",
        "Ramirez",
        com.teammovil.domain.GenderType.MALE,
        getDateFromString("2020-09-01")?.let { it } ?: Date(),
        "password123",
        "7471879473",
        "Eje 7 Norte 45, Ciudad de México"
    ),
    com.teammovil.domain.Adopter(
        "example@gmail.com",
        "Fernanda",
        "Castro",
        "Robledo",
        com.teammovil.domain.GenderType.FEMALE,
        getDateFromString("2020-09-01")?.let { it } ?: Date(),
        "password123",
        "7471879473",
        "Eje 9 Este 145, Ciudad de México"
    ),
    com.teammovil.domain.Adopter(
        "example@gmail.com",
        "Carolina",
        "Caballero",
        "Ramirez",
        com.teammovil.domain.GenderType.FEMALE,
        getDateFromString("2020-09-01")?.let { it } ?: Date(),
        "password123",
        "7471879475",
        "Eje 23 Sur 39, Ciudad de México"
    ),
    com.teammovil.domain.Adopter(
        "example@gmail.com",
        "Jorge",
        "Crespo",
        "Ramirez",
        com.teammovil.domain.GenderType.MALE,
        getDateFromString("2020-09-01")?.let { it } ?: Date(),
        "password123",
        "7471879475",
        "Eje 12 Sur 39, Estado de México"
    )
).toMutableList()

val fakeAdopter = com.teammovil.domain.Adopter(
    "karen@gmail.com",
    "Karen",
    "Pérez",
    "García",
    com.teammovil.domain.GenderType.FEMALE,
    getDateFromString("1981-05-27")?.let { it } ?: Date(),
    "hola1234",
    "5566778899",
    "Ciudad de México"
)

class FakeAdopterExternalDataAccess:
    com.teammovil.data.adopter.AdopterExternalDataAccess {
    override suspend fun login(user: String, password: String): com.teammovil.domain.Adopter {
        return fakeAdopter
    }

    override suspend fun registerAdopter(adopter: com.teammovil.domain.Adopter):Boolean {
        return true
    }

    override suspend fun getAllAdopters(): List<com.teammovil.domain.Adopter> {
        return fakeAdopterList
    }
}

class FakeAdopterStorageDataAccess:
    com.teammovil.data.adopter.AdopterStorageDataAccess {
    override suspend fun saveAdopter(adopter: com.teammovil.domain.Adopter) {

    }

    override suspend fun getAdopter(): com.teammovil.domain.Adopter {
        return fakeAdopter
    }
}