package com.teammovil.pettracker.ui.editregisterpet

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditRegisterPetViewModel(
    var petRepository: PetRepository
): ViewModel() {

    sealed class UiModel {
        class Loading(val show: Boolean): UiModel()
        class SuccessAdvice (): UiModel()
        class ErrorAdvice (val message: String): UiModel()
    }

    private val _petView = MutableLiveData<PetView>()
    val petView: LiveData<PetView> get() = _petView

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> get() = _model
    
    private val _navigation = MutableLiveData<Event<Unit>>()
    val navigation: LiveData<Event<Unit>> get() = _navigation

    private var getPetFirstTime = true

    fun onStartView (id: String?){
        id?.let {
            if (getPetFirstTime) {
                getPetFirstTime = false
                getPet(id)
            }
        }
    }

    fun onSavePet (pet: PetView){
        if(Validations.validateView(pet)){
            savePet(Mapper.mapPet(pet))
        }
        else{
            _petView.value = pet
        }
    }

    fun onSavePetLocal (pet: PetView){
        _petView.value = pet
    }

    fun onClickOkAdvice (){
        _navigation.value = Event(Unit)
    }

    fun onSavePhotoUrl (url: String?){
        petView.value?.mainPhoto?.value = url
    }

    private fun savePet (pet: Pet){
        viewModelScope.launch {
            _model.value = UiModel.Loading(true)
            val result =
                if(pet.id.isEmpty())
                    withContext(Dispatchers.IO){petRepository.registerPet(pet)}
                else
                    withContext(Dispatchers.IO){petRepository.updatePet(pet)}
            _model.value = UiModel.Loading(false)
            if(result) showSuccessAdvice()
            else showRegistrationError()
        }
    }

    private fun getPet (id: String){
        viewModelScope.launch {
            _model.value = UiModel.Loading(true)
            val result = withContext(Dispatchers.IO){petRepository.getPetById(id)}
            _petView.value = Mapper.mapPet(result)
            _model.value = UiModel.Loading(false)
        }
    }

    private fun showSuccessAdvice (){
        _model.value = UiModel.SuccessAdvice()
    }

    private fun showRegistrationError (){
        _model.value = UiModel.ErrorAdvice("Hubo un error al registrar su mascota. Intente más tarde.")
    }
}


