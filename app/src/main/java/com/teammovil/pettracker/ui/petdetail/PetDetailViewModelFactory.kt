package com.teammovil.pettracker.ui.petdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.data.pet.PetRepository
import java.lang.IllegalArgumentException

class PetDetailViewModelFactory (val repository: PetRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PetDetailViewModel::class.java) ) {
            return PetDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown PetDetailViewModel")

    }
}