package com.teammovil.pettracker.di

import com.teammovil.data.pet.PetRepository
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.pettracker.ui.registeredpets.RegisteredPetsViewModel
import com.teammovil.usecases.rescuerPets.GetRescuerPets
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RegisteredPetsModule {

    @Provides
    fun rescuerPetsViewModelProvider(getRescuerPets: GetRescuerPets) = RegisteredPetsViewModel(getRescuerPets)

    @Provides
    @ViewModelScoped
    fun getRescuerPetProvider(petRepository: PetRepository, rescuerRepository: RescuerRepository) = GetRescuerPets(petRepository, rescuerRepository)
}