package com.teammovil.pettracker.data.pet

import com.teammovil.pettracker.domain.Pet

interface PetExternalDataAccess {

    suspend fun getAllPatsFromRescuer (rescuerId: String): List<Pet>

    suspend fun getPetById (petId: String): Pet

    suspend fun registerPet (pet: Pet): Boolean
}