package com.teammovil.data.adopter

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

    suspend fun getAllAdopters() = externalDataAccess.getAllAdopters()

    suspend fun registerAdopter (adopter: com.teammovil.domain.Adopter) = externalDataAccess.registerAdopter(adopter)
}