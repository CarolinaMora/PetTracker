package com.teammovil.data.pet

import com.teammovil.domain.Evidence
import com.teammovil.domain.Pet

interface PetExternalDataAccess {

    suspend fun getAllPatsFromRescuer (rescuerId: String): List<com.teammovil.domain.Pet>

    suspend fun getAllPetsFromAdopter (adopterId: String): List<com.teammovil.domain.Pet>

    suspend fun getPetById (petId: String): com.teammovil.domain.Pet?

    suspend fun registerPet (pet: com.teammovil.domain.Pet, rescuerId: String): Boolean

    suspend fun updatePet (pet: com.teammovil.domain.Pet): Boolean

    suspend fun assignAdopterToPet(petId: String, adopterId: String): Boolean

    suspend fun saveEvidence (petId: String, evidence: com.teammovil.domain.Evidence): Boolean
}