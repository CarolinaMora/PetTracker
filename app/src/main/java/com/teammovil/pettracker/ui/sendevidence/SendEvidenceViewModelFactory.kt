package com.teammovil.pettracker.ui.sendevidence

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.ui.petregistration.PetRegistrationViewModel

class SendEvidenceViewModelFactory(var petRepository: PetRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SendEvidenceViewModel::class.java)){
            return SendEvidenceViewModel(petRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}