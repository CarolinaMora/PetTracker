package com.teammovil.usecases.adopterPets

import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.data.pet.PetRepository
import com.teammovil.domain.Pet

class GetAdopterData(val petRepository: PetRepository, val adopterRepository: AdopterRepository) {

    suspend fun invokePetsAdopter(): List<Pet> = petRepository.getAllPetsFromAdopter(adopterRepository.getAdopter().email)

    suspend fun invokeAdopter() = adopterRepository.getAdopter()
}