package com.teammovil.pettracker.data.pet

import com.teammovil.pettracker.domain.Evidence
import com.teammovil.pettracker.domain.Pet

class PetRepository(private val externalDataAccess: PetExternalDataAccess) {

    suspend fun getAllPatsFromRescuer (rescuerId: String) = externalDataAccess.getAllPatsFromRescuer(rescuerId)

    suspend fun getAllPetsFromAdopter (adopterId: String) = externalDataAccess.getAllPetsFromAdopter(adopterId)

    suspend fun getPetById (petId: String) = externalDataAccess.getPetById(petId)

    suspend fun registerPet (pet: Pet, rescuerId: String) = externalDataAccess.registerPet(pet, rescuerId)

    suspend fun updatePet (pet: Pet) = externalDataAccess.updatePet(pet)

    suspend fun assignAdopterToPet(petId: String, adopterId: String) = externalDataAccess.assignAdopterToPet(petId,adopterId)

    suspend fun saveEvidence (petId:String, evidence: Evidence) = externalDataAccess.saveEvidence(petId, evidence)
}