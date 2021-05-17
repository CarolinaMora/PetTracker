package com.teammovil.pettracker.ui.registeredpets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.usecases.rescuerPets.GetRescuerPets

class RegisteredPetsViewModelFactory(private val getRescuerPets: GetRescuerPets): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisteredPetsViewModel::class.java)){
            return RegisteredPetsViewModel(getRescuerPets) as T
        }
        throw IllegalArgumentException("Unknow RegisteredPetsViewModel")
    }
}