package com.teammovil.usecases.assignadoptertopet

import com.teammovil.data.pet.PetRepository

class AssignAdopterToPetUseCase(val petRepository: PetRepository) {
    suspend fun invoke(petId: String, adopterId: String):Boolean = petRepository.assignAdopterToPet(petId, adopterId)
}