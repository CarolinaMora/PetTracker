package com.teammovil.usecases.petdetail

import com.teammovil.data.pet.PetRepository
import com.teammovil.domain.Pet

class GetPetUseCase(val petRepository: PetRepository) {
    suspend fun invoke(petId: String): Pet? = petRepository.getPetById(petId)
}