package com.teammovil.pettracker.ui.adopterregistration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.usecases.registeradopter.RegisterAdopterUseCase
import java.lang.IllegalArgumentException

class AdopterRegistrationViewModelFactory(val registerAdopterUseCase: RegisterAdopterUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AdopterRegistrationViewModel::class.java)){
            return AdopterRegistrationViewModel(registerAdopterUseCase) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}