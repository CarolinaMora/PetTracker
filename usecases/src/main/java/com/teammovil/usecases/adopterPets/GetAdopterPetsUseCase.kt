package com.teammovil.usecases.adopterPets

import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.data.pet.PetRepository
import com.teammovil.domain.Pet

class GetAdopterPetsUseCase(val petRepository: PetRepository, val adopterRepository: AdopterRepository) {

    suspend fun invoke(): List<Pet> = petRepository.getAllPetsFromAdopter(adopterRepository.getAdopter().email)

}