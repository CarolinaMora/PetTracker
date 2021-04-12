package com.teammovil.pettracker.ui.petregistration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.domain.GenderType
import com.teammovil.pettracker.domain.Pet
import com.teammovil.pettracker.domain.PetType
import com.teammovil.pettracker.getDateFromString
import com.teammovil.pettracker.ui.common.Event
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
        if(validateView(pet)){
            savePet(mapPet(pet))
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

    private fun validateView(pet: PetView): Boolean {
        var valid = true
        if (pet.name.value.isNullOrEmpty()){
            valid = false
            pet.name.valid = false
            pet.name.message = "Campo requerido"
        }
        if (pet.description.value.isNullOrEmpty()){
            valid = false
            pet.description.valid = false
            pet.description.message = "Campo requerido"
        }
        if (pet.race.value.isNullOrEmpty()) {
            valid = false
            pet.race.valid = false
            pet.race.message = "Campo requerido"
        }
        if (pet.gender.value.isNullOrEmpty() || pet.gender.value == "Seleccione el género"){
            valid = false
            pet.gender.valid = false
            pet.gender.message = "Seleccine una opción"
        }
        if(pet.petType.value.isNullOrEmpty() || pet.petType.value == "Seleccione la especie"){
            valid = false
            pet.petType.valid = false
            pet.petType.message = "Seleccine una opción"
        }
        if(pet.approximateDateOfBirth.value == null || getDateFromString(pet.approximateDateOfBirth.value) == null){
            valid = false
            pet.approximateDateOfBirth.valid = false
            pet.approximateDateOfBirth.message = "Campo requerido"
        }
        if(pet.rescueDate.value == null || getDateFromString(pet.rescueDate.value) == null){
            valid = false
            pet.rescueDate.valid = false
            pet.rescueDate.message = "Campo requerido"
        }
        if(pet.mainPhoto.value.isNullOrEmpty()){
            valid = false
            pet.mainPhoto.valid = false
            pet.mainPhoto.message = "Fotografía requerida"
        }
        return valid
    }


    private fun mapPet (origin: PetView): Pet{
        return Pet(
            "",
            origin.name.value!!,
            GenderType.valueOf(origin.gender.value!!),
            origin.race.value!!,
            origin.description.value!!,
            getDateFromString(origin.approximateDateOfBirth.value!!)!!,
            getDateFromString(origin.rescueDate.value!!)!!,
            PetType.valueOf(origin.petType.value!!),
            origin.sterilized.value,
            origin.vaccines.value?.let { it } ?: listOf(),
            origin.dewormings.value?.let { it } ?: listOf(),
            origin.mainPhoto.value!!,
            origin.status.value,
            origin.evidences.value?.let { it } ?: listOf()
        )
    }

    private fun showSuccessAdvice (){
        _model.value = UiModel.SuccessAdvice("Mascota registrada correctamente")
    }

    private fun showRegistrationError (){
        _model.value = UiModel.ErrorAdvice("Hubo un error al registrar su mascota. Intente más tarde.")
    }
}


