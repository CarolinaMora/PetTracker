package com.teammovil.pettracker.data.pet

import com.teammovil.pettracker.domain.Evidence
import com.teammovil.pettracker.domain.Pet

class PetRepository(private val externalDataAccess: PetExternalDataAccess) {

    suspend fun getAllPatsFromRescuer (rescuerId: String) = externalDataAccess.getAllPatsFromRescuer(rescuerId)

    suspend fun getAllPetsFromAdopter (adopterId: String) = externalDataAccess.getAllPetsFromAdopter(adopterId)

    suspend fun getPetById (petId: String) = externalDataAccess.getPetById(petId)

    suspend fun registerPet (pet: Pet) = externalDataAccess.registerPet(pet)

    suspend fun saveEvidence (petId:String, evidence: Evidence) = externalDataAccess.saveEvidence(petId, evidence)
}