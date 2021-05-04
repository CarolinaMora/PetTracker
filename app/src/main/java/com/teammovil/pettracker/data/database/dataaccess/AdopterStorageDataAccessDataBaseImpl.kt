package com.teammovil.pettracker.data.database.dataaccess

import android.content.Context
import com.teammovil.pettracker.data.adopter.AdopterStorageDataAccess
import com.teammovil.pettracker.data.database.Mapper
import com.teammovil.pettracker.data.database.PetsDataBase
import com.teammovil.pettracker.data.database.dao.AdopterDao
import com.teammovil.pettracker.data.database.entities.AdopterEntity
import com.teammovil.pettracker.domain.Adopter

class AdopterStorageDataAccessDataBaseImpl(val context: Context): AdopterStorageDataAccess {
    private val database: PetsDataBase = PetsDataBase.getDatabase(context)

    override suspend fun saveAdopter(adopter: Adopter) {
        val adopterMapToEntity = Mapper.adopterEntityMap(adopter)
        database.adopterDao().deleteAdopter()
        database.adopterDao().addAdopter(adopterMapToEntity)

    }

    override suspend fun getAdopter(): Adopter {
        val adopterResult = database.adopterDao().getAdopter()
        return Mapper.map(adopterResult)
    }
}