package com.teammovil.pettracker.data.database.dataaccess

import android.content.Context
import com.teammovil.data.adopter.AdopterStorageDataAccess
import com.teammovil.pettracker.data.database.Mapper
import com.teammovil.pettracker.data.database.PetsDataBase
import com.teammovil.domain.Adopter

class AdopterStorageDataAccessDataBaseImpl(val context: Context):
    com.teammovil.data.adopter.AdopterStorageDataAccess {
    private val database: PetsDataBase = PetsDataBase.getDatabase(context)

    override suspend fun saveAdopter(adopter: com.teammovil.domain.Adopter) {
        val adopterMapToEntity = Mapper.adopterEntityMap(adopter)
        database.adopterDao().deleteAdopter()
        database.adopterDao().addAdopter(adopterMapToEntity)

    }

    override suspend fun getAdopter(): com.teammovil.domain.Adopter {
        val adopterResult = database.adopterDao().getAdopter()
        return Mapper.map(adopterResult)
    }
}