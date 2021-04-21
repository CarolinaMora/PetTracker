package com.teammovil.pettracker.data.adopter.fakes

import com.teammovil.pettracker.data.adopter.AdopterExternalDataAccess
import com.teammovil.pettracker.data.adopter.AdopterStorageDataAccess
import com.teammovil.pettracker.domain.Adopter
import com.teammovil.pettracker.domain.GenderType
import com.teammovil.pettracker.getDateFromString
import java.util.*


val fakeAdopter = Adopter(
    "1",
    "Karen",
    "Pérez",
    "García",
    GenderType.FEMALE,
    getDateFromString("1981-05-27")?.let{it}?: Date(),
    "karen@gmai.com",
    "hola1234",
    "5566778899",
    "Ciudad de México"
)

class FakeAdopterExternalDataAccess: AdopterExternalDataAccess{
    override suspend fun login(user: String, password: String): Adopter {
        return fakeAdopter
    }

    override suspend fun saveAdopter(adopter: Adopter) {

    }
}

class FakeAdopterStorageDataAccess: AdopterStorageDataAccess{
    override suspend fun saveAdopter(adopter: Adopter) {

    }

    override suspend fun getAdopter(adopter: Adopter): Adopter {
        return fakeAdopter
    }
}