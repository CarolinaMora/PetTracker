package com.teammovil.pettracker.ui.adopterpets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.usecases.adopterPets.GetAdopterPetsUseCase

class AdopterPetsViewModelFactory(private val getAdopterPetsUseCase: GetAdopterPetsUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdopterPetsViewModel::class.java)){
            return AdopterPetsViewModel(getAdopterPetsUseCase) as T
        }
        throw IllegalArgumentException("Unknown AdopterPetsViewModel")
    }
}