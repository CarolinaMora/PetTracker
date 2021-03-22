package com.teammovil.pettracker.data.rescuer

import com.teammovil.pettracker.domain.Rescuer

interface RescuerExternalDataAccess {

    suspend fun login(user: String, password: String): Rescuer
}