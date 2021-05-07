package com.teammovil.pettracker.ui.registeredpets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.usecases.rescuerPets.GetRescuerData

class RegisteredPetsViewModelFactory(private val getRescuerData: GetRescuerData): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisteredPetsViewModel::class.java)){
            return RegisteredPetsViewModel(getRescuerData) as T
        }
        throw IllegalArgumentException("Unknow RegisteredPetsViewModel")
    }
}