package com.teammovil.pettracker.ui.sendevidence

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.data.pet.PetRepository

class SendEvidenceViewModelFactory(var petRepository: PetRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SendEvidenceViewModel::class.java)){
            return SendEvidenceViewModel(petRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}