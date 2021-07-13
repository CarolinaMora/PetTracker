package com.teammovil.data.adopter

import com.teammovil.domain.Adopter

interface AdopterExternalDataAccess {

    suspend fun login(user: String, password: String): Adopter?

    suspend fun registerAdopter (adopter: Adopter) : Boolean

    suspend fun getAllAdopters():List<Adopter>
}