package com.teammovil.data.rescuer

import com.teammovil.domain.Rescuer

interface RescuerStorageDataAccess {

    suspend fun saveRescuer (rescuer: com.teammovil.domain.Rescuer)

    suspend fun getRescuer (): com.teammovil.domain.Rescuer?
}