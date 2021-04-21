package com.teammovil.pettracker.ui.registeredpets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.data.rescuer.RescuerRepository
import java.lang.IllegalArgumentException

class RegisteredPetsViewModelFactory(val petRepository: PetRepository, val rescuerRepository: RescuerRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisteredPetsViewModel::class.java)){
            return RegisteredPetsViewModel(petRepository, rescuerRepository) as T
        }
        throw IllegalArgumentException("Unknow RegisteredPetsViewModel")
    }
}