package com.teammovil.pettracker.di

import com.teammovil.data.pet.PetRepository
import com.teammovil.usecases.petdetail.GetPetUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class PetDetailModule {

    @Provides
    fun getPetUseCaseProvider(petRepository: PetRepository) = GetPetUseCase(petRepository)

}
