package com.teammovil.pettracker.di

import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.data.pet.PetRepository
import com.teammovil.usecases.adopterPets.GetAdopterPetsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class AdopterPetsModule {

    @Provides
    fun getAdopterPetProvider(petRepository: PetRepository, adopterRepository: AdopterRepository) = GetAdopterPetsUseCase(petRepository, adopterRepository)
}