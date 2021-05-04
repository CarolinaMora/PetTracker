package com.teammovil.data.rescuer

import com.teammovil.domain.Rescuer

interface RescuerExternalDataAccess {

    suspend fun login(user: String, password: String): com.teammovil.domain.Rescuer?
    suspend fun registerRescuer(rescuer: com.teammovil.domain.Rescuer): Boolean
}