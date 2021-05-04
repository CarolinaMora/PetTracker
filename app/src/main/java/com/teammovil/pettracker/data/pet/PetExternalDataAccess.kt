package com.teammovil.pettracker.data.pet

import com.teammovil.pettracker.domain.Evidence
import com.teammovil.pettracker.domain.Pet

interface PetExternalDataAccess {

    suspend fun getAllPatsFromRescuer (rescuerId: String): List<Pet>

    suspend fun getAllPetsFromAdopter (adopterId: String): List<Pet>

    suspend fun getPetById (petId: String): Pet?

    suspend fun registerPet (pet: Pet, rescuerId: String): Boolean

    suspend fun updatePet (pet: Pet): Boolean

    suspend fun assignAdopterToPet(petId: String, adopterId: String): Boolean

    suspend fun saveEvidence (petId: String, evidence: Evidence): Boolean
}