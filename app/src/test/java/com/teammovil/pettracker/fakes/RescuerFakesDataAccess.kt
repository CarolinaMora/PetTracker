package com.teammovil.pettracker.fakes

import com.teammovil.data.rescuer.RescuerExternalDataAccess
import com.teammovil.data.rescuer.RescuerStorageDataAccess
import com.teammovil.pettracker.getDateFromString
import java.util.*

val fakeRescuer = com.teammovil.domain.Rescuer(
    "1",
    "Jefferson Rescata",
    "Rescatista independiente",
    "Ecuador",
    "jefferson@gmail.com",
    "hola1234",
    "5566778899",
    getDateFromString("2015-07-19")?.let { it } ?: Date()
)

class RescuerFakeExternalDataAccess :
    RescuerExternalDataAccess {
    override suspend fun login(user: String, password: String): com.teammovil.domain.Rescuer {
        return fakeRescuer
    }

    override suspend fun registerRescuer(rescuer: com.teammovil.domain.Rescuer): Boolean {
        return true
    }
}

class RescuerFakeStorageDataAccess :
    RescuerStorageDataAccess {
    override suspend fun saveRescuer(rescuer: com.teammovil.domain.Rescuer) {

    }

    override suspend fun getRescuer(): com.teammovil.domain.Rescuer {
        return fakeRescuer
    }
}