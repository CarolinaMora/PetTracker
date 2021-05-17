package com.teammovil.pettracker.ui.editregisterpet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teammovil.data.pet.PetRepository
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.usecases.editpet.EditPetUseCase
import com.teammovil.usecases.registerpet.RegisterPetUseCase

class EditRegisterPetViewModelFactory(var registerPetUseCase: RegisterPetUseCase, var editPetUseCase: EditPetUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EditRegisterPetViewModel::class.java)){
            return EditRegisterPetViewModel(editPetUseCase, registerPetUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
