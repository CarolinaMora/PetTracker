package com.teammovil.pettracker.di

import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.data.pet.PetRepository
import com.teammovil.pettracker.ui.adopterpets.AdopterPetsViewModel
import com.teammovil.usecases.adopterPets.GetAdopterPetsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class AdopterPetsModule {

    @Provides
    fun adopterPetsViewModelProvider(getAdopterPetsUseCase: GetAdopterPetsUseCase) = AdopterPetsViewModel(getAdopterPetsUseCase)

    @Provides
    @ViewModelScoped
    fun getAdopterPetProvider(petRepository: PetRepository, adopterRepository: AdopterRepository) = GetAdopterPetsUseCase(petRepository, adopterRepository)
}