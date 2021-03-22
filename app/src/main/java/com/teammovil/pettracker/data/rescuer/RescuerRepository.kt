package com.teammovil.pettracker.data.rescuer

class RescuerRepository(private val externalDataAccess: RescuerExternalDataAccess, private val storageDataAccess: RescuerStorageDataAccess) {

    suspend fun login(user: String, password: String): Boolean{
        val rescuer = externalDataAccess.login(user, password)
        storageDataAccess.saveRescuer(rescuer)
        return true
    }

    suspend fun getRescuer () = storageDataAccess.getRescuer()
}