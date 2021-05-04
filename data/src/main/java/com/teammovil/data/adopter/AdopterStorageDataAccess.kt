package com.teammovil.data.adopter

import com.teammovil.domain.Adopter

interface AdopterStorageDataAccess {

    suspend fun saveAdopter (adopter: com.teammovil.domain.Adopter)

    suspend fun getAdopter (): com.teammovil.domain.Adopter
}