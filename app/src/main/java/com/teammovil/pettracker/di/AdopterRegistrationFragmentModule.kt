package com.teammovil.pettracker.di

import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.usecases.registeradopter.RegisterAdopterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class AdopterRegistrationFragmentModule {

    @Provides
    fun provideRegisterAdopterUseCase(adopterRepository: AdopterRepository) = RegisterAdopterUseCase(adopterRepository)
}