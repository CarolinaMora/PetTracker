package com.teammovil.pettracker.data.adopter

import com.teammovil.pettracker.data.adopter.fakes.FakeAdopterExternalDataAccess

class AdopterRepository(private val externalDataAccess: FakeAdopterExternalDataAccess, private val storageDataAccess: AdopterStorageDataAccess) {

    suspend fun login(user: String, password: String): Boolean{
        val adopter = externalDataAccess.login(user, password)
        storageDataAccess.saveAdopter(adopter)
        return true
    }

    suspend fun getAdopter () = storageDataAccess.getAdopter()
}