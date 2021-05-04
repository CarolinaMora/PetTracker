package com.teammovil.pettracker.ui.adopterlogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.data.adopter.AdopterRepository

class AdopterLoginViewModelFactory (private val adopterRepository: AdopterRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AdopterLoginViewModel::class.java)){
            return AdopterLoginViewModel(adopterRepository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}