package com.teammovil.pettracker.data.pet

import com.teammovil.pettracker.domain.Pet

class PetRepository(private val externalDataAccess: PetExternalDataAccess) {

    suspend fun getAllPatsFromRescuer (rescuerId: String) = externalDataAccess.getAllPatsFromRescuer(rescuerId)

    suspend fun getPetById (petId: String) = externalDataAccess.getPetById(petId)

    suspend fun registerPet (pet: Pet) = externalDataAccess.registerPet(pet)

    suspend fun updatePet (pet: Pet) = externalDataAccess.updatePet(pet)
}