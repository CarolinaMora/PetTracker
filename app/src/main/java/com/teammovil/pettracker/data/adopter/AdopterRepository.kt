package com.teammovil.pettracker.data.adopter

import com.teammovil.pettracker.domain.Adopter

class AdopterRepository (private val externalDataAccess: AdopterExternalDataAccess, private val storageDataAccess: AdopterStorageDataAccess) {

    suspend fun login(user: String, password: String): Boolean{
        val adopter = externalDataAccess.login(user, password)
        if(adopter!=null) {
            storageDataAccess.saveAdopter(adopter)
            return true
        }
        return false
    }

    suspend fun getAdopter () = storageDataAccess.getAdopter()

    suspend fun registerAdopter (adopter:Adopter) = externalDataAccess.registerAdopter(adopter)
}