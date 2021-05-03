package com.teammovil.pettracker.data.rescuer

import com.teammovil.pettracker.domain.Rescuer

interface RescuerStorageDataAccess {

    suspend fun saveRescuer (rescuer: Rescuer)

    suspend fun getRescuer (): Rescuer?
}