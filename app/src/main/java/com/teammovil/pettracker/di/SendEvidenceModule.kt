package com.teammovil.pettracker.di

import com.teammovil.data.pet.PetRepository
import com.teammovil.usecases.SaveEvidenceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class SendEvidenceModule {

    @Provides
    fun sendEvidenceUseCaseProvider(petRepository: PetRepository) = SaveEvidenceUseCase(petRepository)


}