package com.teammovil.pettracker.di

import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.usecases.loginadopter.LoginAdopterUseCase
import com.teammovil.usecases.loginrescuer.LoginRescuerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class RescuerLoginFragmentModule {

    @Provides
    fun providerRescuerLoginUseCase(rescuerRepository: RescuerRepository)= LoginRescuerUseCase(rescuerRepository)
}