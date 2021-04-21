package com.teammovil.pettracker.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.pettracker.data.adopter.AdopterRepository

class AdopterLoginViewModelFactory (private val adopterRepository: AdopterRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AdopterLoginViewModelFactory::class.java)){
            return AdopterLoginViewModelFactory(adopterRepository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}