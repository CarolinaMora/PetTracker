package com.teammovil.data.rescuer

import com.teammovil.domain.Rescuer

interface RescuerExternalDataAccess {

    suspend fun login(user: String, password: String): Rescuer?
    suspend fun registerRescuer(rescuer: Rescuer): Boolean
}