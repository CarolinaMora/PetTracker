package com.teammovil.pettracker.ui

import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.usecases.loginadopter.LoginAdopterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class AdopterLoginFragmentModule {

    @Provides
    fun providerAdopterLoginUseCase(adopterRepository: AdopterRepository) = LoginAdopterUseCase(adopterRepository)
}