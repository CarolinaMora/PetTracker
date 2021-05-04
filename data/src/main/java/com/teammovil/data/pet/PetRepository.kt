package com.teammovil.data.pet

import com.teammovil.data.pet.PetExternalDataAccess

class PetRepository(private val externalDataAccess: PetExternalDataAccess) {

    suspend fun getAllPatsFromRescuer (rescuerId: String) = externalDataAccess.getAllPatsFromRescuer(rescuerId)

    suspend fun getAllPetsFromAdopter (adopterId: String) = externalDataAccess.getAllPetsFromAdopter(adopterId)

    suspend fun getPetById (petId: String) = externalDataAccess.getPetById(petId)

    suspend fun registerPet (pet: com.teammovil.domain.Pet, rescuerId: String) = externalDataAccess.registerPet(pet, rescuerId)

    suspend fun updatePet (pet: com.teammovil.domain.Pet) = externalDataAccess.updatePet(pet)

    suspend fun assignAdopterToPet(petId: String, adopterId: String) = externalDataAccess.assignAdopterToPet(petId,adopterId)

    suspend fun saveEvidence (petId:String, evidence: com.teammovil.domain.Evidence) = externalDataAccess.saveEvidence(petId, evidence)
}