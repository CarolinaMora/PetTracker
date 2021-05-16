package com.teammovil.pettracker.ui.rescuerlogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.usecases.userrescuer.GetUserRescuer

class RescuerLoginViewModelFactory (private val rescuerRepository: GetUserRescuer): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RescuerLoginViewModel::class.java)){
            return RescuerLoginViewModel(rescuerRepository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}