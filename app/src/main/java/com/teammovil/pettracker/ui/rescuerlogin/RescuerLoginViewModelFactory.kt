package com.teammovil.pettracker.ui.rescuerlogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.usecases.loginrescuer.LoginRescuerUseCase

class RescuerLoginViewModelFactory (private val rescuerUseCaseRepository: LoginRescuerUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RescuerLoginViewModel::class.java)){
            return RescuerLoginViewModel(rescuerUseCaseRepository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}