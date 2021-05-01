package com.teammovil.pettracker.ui.adopterpets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.pettracker.data.adopter.AdopterRepository
import com.teammovil.pettracker.data.pet.PetRepository
import java.lang.IllegalArgumentException

class AdopterPetsViewModelFactory(val petRepository: PetRepository, val adopterRepository: AdopterRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdopterPetsViewModel::class.java)){
            return AdopterPetsViewModel(petRepository, adopterRepository) as T
        }
        throw IllegalArgumentException("Unknown AdopterPetsViewModel")
    }
}