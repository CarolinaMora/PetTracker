package com.teammovil.pettracker.ui.editregisterpet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.data.rescuer.RescuerRepository

class EditRegisterPetViewModelFactory(var petRepository: PetRepository, var rescuerRepository: RescuerRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EditRegisterPetViewModel::class.java)){
            return EditRegisterPetViewModel(petRepository, rescuerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
