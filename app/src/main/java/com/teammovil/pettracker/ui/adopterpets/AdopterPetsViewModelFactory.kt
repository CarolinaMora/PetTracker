package com.teammovil.pettracker.ui.adopterpets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.usecases.adopterPets.GetAdopterData

class AdopterPetsViewModelFactory(private val getAdopterData: GetAdopterData): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdopterPetsViewModel::class.java)){
            return AdopterPetsViewModel(getAdopterData) as T
        }
        throw IllegalArgumentException("Unknown AdopterPetsViewModel")
    }
}