package com.teammovil.pettracker.ui.assigningadoptertopet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.data.pet.PetRepository
import java.lang.IllegalArgumentException

class AdopterViewModelFactory(val petRepository: PetRepository, val adopterRepository: AdopterRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdopterViewModel::class.java)){
            return AdopterViewModel(adopterRepository,petRepository) as T
        }
        throw IllegalArgumentException("Unknow AdopterViewModel")
    }
}