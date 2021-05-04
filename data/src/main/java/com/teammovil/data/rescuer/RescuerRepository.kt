package com.teammovil.data.rescuer

import com.teammovil.data.rescuer.RescuerExternalDataAccess
import com.teammovil.data.rescuer.RescuerStorageDataAccess

class RescuerRepository(
    private val externalDataAccess: RescuerExternalDataAccess,
    private val storageDataAccess: RescuerStorageDataAccess
) {

    suspend fun login(user: String, password: String): Boolean{
        val rescuer = externalDataAccess.login(user, password)
        if(rescuer!=null) {
            storageDataAccess.saveRescuer(rescuer)
            return true
        }
        return false
    }

    suspend fun getRescuer () = storageDataAccess.getRescuer()

    suspend fun registerRescuer (rescuer: com.teammovil.domain.Rescuer) = externalDataAccess.registerRescuer(rescuer)
}