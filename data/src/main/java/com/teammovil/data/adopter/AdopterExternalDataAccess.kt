package com.teammovil.data.adopter

import com.teammovil.domain.Adopter

interface AdopterExternalDataAccess {

    suspend fun login(user: String, password: String): com.teammovil.domain.Adopter?

    suspend fun registerAdopter (adopter: com.teammovil.domain.Adopter) : Boolean

    suspend fun getAllAdopters():List<com.teammovil.domain.Adopter>
}