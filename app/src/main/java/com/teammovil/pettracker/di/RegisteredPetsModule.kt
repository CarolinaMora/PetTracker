package com.teammovil.pettracker.di

import com.teammovil.data.pet.PetRepository
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.pettracker.ui.registeredpets.RegisteredPetsViewModel
import com.teammovil.usecases.rescuerPets.GetRescuerPets
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class RegisteredPetsModule {

    @Provides
    fun getRescuerPetProvider(petRepository: PetRepository, rescuerRepository: RescuerRepository) = GetRescuerPets(petRepository, rescuerRepository)
}