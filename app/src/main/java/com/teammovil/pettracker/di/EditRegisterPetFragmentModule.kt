package com.teammovil.pettracker.di

import com.teammovil.data.pet.PetRepository
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.usecases.editpet.EditPetUseCase
import com.teammovil.usecases.petdetail.GetPetUseCase
import com.teammovil.usecases.registerpet.RegisterPetUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class EditRegisterPetFragmentModule {

    @Provides
    fun provideEditPetUseCase (petRepository: PetRepository) = EditPetUseCase(petRepository)

    @Provides
    fun provideRegisterPetUseCase (
        rescuerRepository: RescuerRepository,
        petRepository: PetRepository
    ) = RegisterPetUseCase(rescuerRepository, petRepository)

    @Provides
    fun provideGetPetUseCase (petRepository: PetRepository) = GetPetUseCase(petRepository)
}