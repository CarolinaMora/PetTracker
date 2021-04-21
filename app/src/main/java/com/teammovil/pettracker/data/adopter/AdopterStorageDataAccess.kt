package com.teammovil.pettracker.data.adopter

import com.teammovil.pettracker.domain.Adopter

interface AdopterStorageDataAccess {

    suspend fun saveAdopter (adopter: Adopter)

    suspend fun getAdopter (adopter: Adopter): Adopter
}