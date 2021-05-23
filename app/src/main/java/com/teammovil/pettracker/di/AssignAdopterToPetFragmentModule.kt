package com.teammovil.pettracker.di

import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.data.pet.PetRepository
import com.teammovil.usecases.assignadoptertopet.AssignAdopterToPetUseCase
import com.teammovil.usecases.getalladopters.GetAllAdoptersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class AssignAdopterToPetFragmentModule {

    @Provides
    fun provideAssignAdopterToPetUseCase(petRepository: PetRepository) = AssignAdopterToPetUseCase(petRepository)

    @Provides
    fun provideGetAllAdoptersUseCase(adopterRepository: AdopterRepository) = GetAllAdoptersUseCase(adopterRepository)

}