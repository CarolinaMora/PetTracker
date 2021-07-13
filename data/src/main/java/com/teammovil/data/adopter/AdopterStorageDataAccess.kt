package com.teammovil.data.adopter

import com.teammovil.domain.Adopter

interface AdopterStorageDataAccess {

    suspend fun saveAdopter (adopter: Adopter)

    suspend fun getAdopter (): Adopter
}