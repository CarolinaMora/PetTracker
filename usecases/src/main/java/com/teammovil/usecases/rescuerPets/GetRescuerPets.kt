package com.teammovil.usecases.rescuerPets

import com.teammovil.data.pet.PetRepository
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.domain.Pet

class GetRescuerPets(val petRepository: PetRepository, val rescuerRepository: RescuerRepository) {

    suspend fun invoke(): List<Pet>? = rescuerRepository.getRescuer()?.let {
        petRepository.getAllPatsFromRescuer(it.email)
    }

}