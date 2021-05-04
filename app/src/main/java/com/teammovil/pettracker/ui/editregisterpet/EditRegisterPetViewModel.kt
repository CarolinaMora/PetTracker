package com.teammovil.pettracker.ui.editregisterpet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.data.rescuer.RescuerRepository
import com.teammovil.pettracker.domain.Pet
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.pettracker.ui.common.Mapper
import com.teammovil.pettracker.ui.common.PetView
import com.teammovil.pettracker.ui.common.Validations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditRegisterPetViewModel(
    var petRepository: PetRepository,
    var rescuerRepository: RescuerRepository
): ViewModel() {

    sealed class UiModel {
        class Loading(val show: Boolean): UiModel()
        class SuccessAdvice (): UiModel()
        class ErrorAdvice (val message: String): UiModel()
    }

    sealed class UiEvents {
        class GoToSendEvidence(val petId: String): UiEvents()
        class GoToAssignAdopter(val petId: String): UiEvents()
        object GoBack: UiEvents()
    }

    private val _petView = MutableLiveData<PetView>()
    val petView: LiveData<PetView> get() = _petView

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> get() = _model
    
    private val _navigation = MutableLiveData<Event<UiEvents>>()
    val navigation: LiveData<Event<UiEvents>> get() = _navigation

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
        _navigation.value = Event(UiEvents.GoBack)
    }

    fun onSavePhotoUrl (url: String?){
        petView.value?.mainPhoto?.value = url
    }

    private fun savePet (pet: Pet){
        viewModelScope.launch {
            _model.value = UiModel.Loading(true)
            val resultRescuer = withContext(Dispatchers.IO) {rescuerRepository.getRescuer() }
            if(resultRescuer!=null) {
                val result =
                    if (pet.id.isEmpty())
                        withContext(Dispatchers.IO) {
                            petRepository.registerPet(
                                pet,
                                resultRescuer.email
                            )
                        }
                    else
                        withContext(Dispatchers.IO) { petRepository.updatePet(pet) }
                _model.value = UiModel.Loading(false)
                if (result) showSuccessAdvice()
                else showRegistrationError()
            } else showRegistrationError()
        }
    }

    private fun getPet (id: String){
        viewModelScope.launch {
            _model.value = UiModel.Loading(true)
            val result = withContext(Dispatchers.IO){petRepository.getPetById(id)}
            _model.value = UiModel.Loading(false)
            if(result!=null)
                _petView.value = Mapper.mapPet(result)
        }
    }

    private fun showSuccessAdvice (){
        _model.value = UiModel.SuccessAdvice()
    }

    private fun showRegistrationError (){
        _model.value = UiModel.ErrorAdvice("Hubo un error al registrar su mascota. Intente más tarde.")
    }

    fun onSendEvidence(petId: String) {
        _navigation.value = Event(UiEvents.GoToSendEvidence(petId))
    }

    fun onAssignAdopter (petId: String){
        _navigation.value = Event(UiEvents.GoToAssignAdopter(petId))
    }
}


