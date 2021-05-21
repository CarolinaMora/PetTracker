package com.teammovil.pettracker.ui.petdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.usecases.petdetail.GetPetUseCase
import java.lang.IllegalArgumentException

class PetDetailViewModelFactory (var getPetUseCase: GetPetUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PetDetailViewModel::class.java) ) {
            return PetDetailViewModel(getPetUseCase) as T
        }
        throw IllegalArgumentException("Unknown PetDetailViewModel")

    }
}