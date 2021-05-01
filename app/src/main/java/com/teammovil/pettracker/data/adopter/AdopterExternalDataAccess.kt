package com.teammovil.pettracker.data.adopter

import com.teammovil.pettracker.domain.Adopter

interface AdopterExternalDataAccess {

    suspend fun login(user: String, password: String): Adopter?

    suspend fun registerAdopter (adopter: Adopter) : Boolean
}