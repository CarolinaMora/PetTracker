package com.teammovil.pettracker.data.rescuer

import com.teammovil.pettracker.domain.Rescuer

class RescuerRepository(
    private val externalDataAccess: RescuerExternalDataAccess,
    private val storageDataAccess: RescuerStorageDataAccess) {

    suspend fun login(user: String, password: String): Boolean{
        val rescuer = externalDataAccess.login(user, password)
        if(rescuer!=null) {
            storageDataAccess.saveRescuer(rescuer)
            return true
        }
        return false
    }

    suspend fun getRescuer () = storageDataAccess.getRescuer()

    suspend fun registerRescuer (rescuer: Rescuer) = externalDataAccess.registerRescuer(rescuer)
}