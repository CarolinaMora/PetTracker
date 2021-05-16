package com.teammovil.pettracker.ui.adopterlogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.usecases.loginadopter.LoginAdopterUseCase


class AdopterLoginViewModelFactory (
    private val adopterUseCaseRepository: LoginAdopterUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AdopterLoginViewModel::class.java)){
            return AdopterLoginViewModel(adopterUseCaseRepository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}