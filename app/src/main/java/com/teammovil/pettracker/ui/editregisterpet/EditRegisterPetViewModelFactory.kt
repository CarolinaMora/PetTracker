package com.teammovil.pettracker.ui.editregisterpet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.pettracker.data.pet.PetRepository

class EditRegisterPetViewModelFactory(var petRepository: PetRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EditRegisterPetViewModel::class.java)){
            return EditRegisterPetViewModel(petRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
