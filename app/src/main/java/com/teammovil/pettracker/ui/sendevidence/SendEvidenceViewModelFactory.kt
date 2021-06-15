package com.teammovil.pettracker.ui.sendevidence

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.usecases.SaveEvidenceUseCase
import kotlinx.coroutines.Dispatchers

class SendEvidenceViewModelFactory(var saveEvidenceUseCase: SaveEvidenceUseCase) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SendEvidenceViewModel::class.java)){
            return SendEvidenceViewModel( saveEvidenceUseCase, Dispatchers.Unconfined) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}