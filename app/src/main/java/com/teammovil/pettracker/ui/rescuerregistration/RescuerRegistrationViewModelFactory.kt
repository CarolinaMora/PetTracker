package com.teammovil.pettracker.ui.rescuerregistration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.usecases.registerrescuer.RegisterRescuerUseCase
import java.lang.IllegalArgumentException

class RescuerRegistrationViewModelFactory(val registerRescuerUseCase: RegisterRescuerUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RescuerRegistrationViewModel::class.java)){
            return RescuerRegistrationViewModel(registerRescuerUseCase) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}