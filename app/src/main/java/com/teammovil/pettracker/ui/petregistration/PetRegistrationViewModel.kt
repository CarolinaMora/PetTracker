package com.teammovil.pettracker.ui.petregistration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.domain.Pet
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.pettracker.ui.common.Mapper
import com.teammovil.pettracker.ui.common.PetView
import com.teammovil.pettracker.ui.common.Validations
import kotlinx.coroutines.launch

class PetRegistrationViewModel(
    var petRepository: PetRepository
): ViewModel() {

    sealed class UiModel {
        object Loading: UiModel()
        class PetError(val petView: PetView): UiModel()
        class SuccessAdvice (val message: String): UiModel()
        class ErrorAdvice (val message: String): UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> get() = _model
    
    private val _navigation = MutableLiveData<Event<Unit>>()
    val navigation: LiveData<Event<Unit>> get() = _navigation

    fun onSavePet (pet: PetView){
        if(Validations.validateView(pet)){
            savePet(Mapper.mapPet(pet))
        }
        else{
            _model.value = UiModel.PetError(pet)
        }
    }

    fun onClickOkAdvice (){
        _navigation.value = Event(Unit)
    }

    private fun savePet (pet: Pet){
        viewModelScope.launch {
            _model.value = UiModel.Loading
            val result = petRepository.registerPet(pet)
            if(result) showSuccessAdvice()
            else showRegistrationError()
        }
    }

    private fun showSuccessAdvice (){
        _model.value = UiModel.SuccessAdvice("Mascota registrada correctamente")
    }

    private fun showRegistrationError (){
        _model.value = UiModel.ErrorAdvice("Hubo un error al registrar su mascota. Intente más tarde.")
    }
}


