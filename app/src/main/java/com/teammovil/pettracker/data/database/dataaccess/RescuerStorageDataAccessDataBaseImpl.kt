package com.teammovil.pettracker.data.database.dataaccess

import android.content.Context
import com.teammovil.pettracker.data.database.Mapper
import com.teammovil.pettracker.data.database.PetsDataBase
import com.teammovil.data.rescuer.RescuerStorageDataAccess

class RescuerStorageDataAccessDataBaseImpl(val context: Context):
    RescuerStorageDataAccess {
    private val database: PetsDataBase = PetsDataBase.getDatabase(context)

    override suspend fun saveRescuer(rescuer: com.teammovil.domain.Rescuer) {
        val rescuerEntity = Mapper.map(rescuer)

        database.rescuerDao().deleteRescuer()
        database.rescuerDao().addRescuer(rescuerEntity)

    }

    override suspend fun getRescuer(): com.teammovil.domain.Rescuer {
        val rescuerResult = database.rescuerDao().getRescuer()
        return Mapper.map(rescuerResult)
    }


}