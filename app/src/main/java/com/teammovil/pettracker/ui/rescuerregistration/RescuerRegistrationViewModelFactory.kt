package com.teammovil.pettracker.ui.rescuerregistration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.pettracker.data.adopter.AdopterRepository
import com.teammovil.pettracker.data.rescuer.RescuerRepository
import java.lang.IllegalArgumentException

class RescuerRegistrationViewModelFactory(val rescuerRepository: RescuerRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RescuerRegistrationViewModel::class.java)){
            return RescuerRegistrationViewModel(rescuerRepository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}