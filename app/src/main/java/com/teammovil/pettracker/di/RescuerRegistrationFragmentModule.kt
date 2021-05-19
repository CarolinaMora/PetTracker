package com.teammovil.pettracker.di

import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.pettracker.ui.rescuerregistration.RescuerRegistrationViewModel
import com.teammovil.usecases.registerrescuer.RegisterRescuerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class RescuerRegistrationFragmentModule {

    @Provides
    fun provideRescuerRegistrationViewModel (registerRescuerUseCase: RegisterRescuerUseCase) = RescuerRegistrationViewModel(registerRescuerUseCase)

    @Provides
    fun provideRegisterRescuerUseCase (rescuerRepository: RescuerRepository) = RegisterRescuerUseCase(rescuerRepository)

}