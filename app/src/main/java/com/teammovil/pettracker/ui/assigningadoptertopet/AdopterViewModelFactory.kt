package com.teammovil.pettracker.ui.assigningadoptertopet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.usecases.assignadoptertopet.AssignAdopterToPetUseCase
import com.teammovil.usecases.getalladopters.GetAllAdoptersUseCase
import java.lang.IllegalArgumentException

class AdopterViewModelFactory(val assignAdopterToPetUseCase: AssignAdopterToPetUseCase, val getAllAdoptersUseCase: GetAllAdoptersUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdopterViewModel::class.java)){
            return AdopterViewModel(assignAdopterToPetUseCase,getAllAdoptersUseCase) as T
        }
        throw IllegalArgumentException("Unknow AdopterViewModel")
    }
}