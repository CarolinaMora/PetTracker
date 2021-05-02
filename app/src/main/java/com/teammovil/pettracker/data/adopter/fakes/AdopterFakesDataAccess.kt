package com.teammovil.pettracker.data.adopter.fakes

import com.teammovil.pettracker.data.adopter.AdopterExternalDataAccess
import com.teammovil.pettracker.data.adopter.AdopterStorageDataAccess
import com.teammovil.pettracker.domain.Adopter
import com.teammovil.pettracker.domain.GenderType
import com.teammovil.pettracker.getDateFromString
import java.util.*

val fakeAdopterList: MutableList<Adopter> = listOf(
    Adopter(
        "1",
        "María",
        "Cardenaz",
        "Gonzalez",
        GenderType.FEMALE,
        getDateFromString("2020-09-01")?.let { it }?: Date(),
        "example@gmail.com",
        "password123",
        "7471879475",
        "Eje 23 Sur 39, Ciudad de México"
    ),
    Adopter(
        "2",
        "Alberto",
        "Martínez",
    "Robledo",
        GenderType.MALE,
        getDateFromString("2020-09-01")?.let { it }?: Date(),
        "example@gmail.com",
        "password123",
        "747187947389",
        "Eje 8 Sur 84, Ciudad de México"
    ),
    Adopter(
        "3",
        "Adrian",
        "Guzman",
        "Ramirez",
        GenderType.MALE,
        getDateFromString("2020-09-01")?.let { it }?: Date(),
        "example@gmail.com",
        "password123",
        "7471879473",
        "Eje 7 Norte 45, Ciudad de México"
    ),
    Adopter(
        "4",
        "Fernanda",
        "Castro",
        "Robledo",
        GenderType.FEMALE,
        getDateFromString("2020-09-01")?.let { it }?: Date(),
        "example@gmail.com",
        "password123",
        "7471879473",
        "Eje 9 Este 145, Ciudad de México"
    ),
    Adopter(
        "5",
        "Carolina",
        "Caballero",
        "Ramirez",
        GenderType.FEMALE,
        getDateFromString("2020-09-01")?.let { it }?: Date(),
        "example@gmail.com",
        "password123",
        "7471879475",
        "Eje 23 Sur 39, Ciudad de México"
    ),
    Adopter(
        "6",
        "Jorge",
        "Crespo",
        "Ramirez",
        GenderType.MALE,
        getDateFromString("2020-09-01")?.let { it }?: Date(),
        "example@gmail.com",
        "password123",
        "7471879475",
        "Eje 12 Sur 39, Estado de México"
    )
).toMutableList()

val fakeAdopter = Adopter(
    "karen@gmai.com",
    "Karen",
    "Pérez",
    "García",
    GenderType.FEMALE,
    getDateFromString("1981-05-27")?.let{it}?: Date(),
    "hola1234",
    "5566778899",
    "Ciudad de México"
)

class FakeAdopterExternalDataAccess: AdopterExternalDataAccess{
    override suspend fun login(user: String, password: String): Adopter {
        return fakeAdopter
    }

    override suspend fun saveAdopter(adopter: Adopter):Boolean {
        return true
    }

    override suspend fun getAllAdopters(): List<Adopter> {
        return fakeAdopterList
    }
}

class FakeAdopterStorageDataAccess: AdopterStorageDataAccess{
    override suspend fun saveAdopter(adopter: Adopter) {

    }

    override suspend fun getAdopter(): Adopter {
        return fakeAdopter
    }
}