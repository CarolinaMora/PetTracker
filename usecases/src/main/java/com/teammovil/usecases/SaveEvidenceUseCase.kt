package com.teammovil.usecases

import com.teammovil.data.pet.PetRepository
import com.teammovil.domain.Evidence

class SaveEvidenceUseCase (private val petRepository: PetRepository) {

    suspend fun invoke (petId:String, evidence: Evidence) = petRepository.saveEvidence(petId, evidence)
    
}