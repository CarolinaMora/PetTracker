package com.teammovil.pettracker.ui.petregistration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.pettracker.data.pet.PetRepository

class PetRegistrationViewModelFactory(var petRepository: PetRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PetRegistrationViewModel::class.java)){
            return PetRegistrationViewModel(petRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
