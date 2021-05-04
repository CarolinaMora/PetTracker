package com.teammovil.pettracker.data.rescuer.fakes

import com.teammovil.pettracker.data.rescuer.RescuerExternalDataAccess
import com.teammovil.pettracker.data.rescuer.RescuerStorageDataAccess
import com.teammovil.pettracker.domain.Rescuer
import com.teammovil.pettracker.getDateFromString
import java.util.*

val fakeRescuer = Rescuer(
    "1",
    "Jefferson Rescata",
    "Rescatista independiente",
    "Ecuador",
    "jefferson@gmail.com",
    "hola1234",
    "5566778899",
    getDateFromString("2015-07-19")?.let{it}?:Date()
)

class RescuerFakeExternalDataAccess : RescuerExternalDataAccess{
    override suspend fun login(user: String, password: String): Rescuer {
        return fakeRescuer
    }

    override suspend fun registerRescuer(rescuer: Rescuer): Boolean {
        return true
    }
}

class RescuerFakeStorageDataAccessNOTUSEANYMORE : RescuerStorageDataAccess {
    override suspend fun saveRescuer(rescuer: Rescuer) {

    }

    override suspend fun getRescuer(): Rescuer {
        return fakeRescuer
    }
}