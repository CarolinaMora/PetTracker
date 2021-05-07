package com.teammovil.pettracker.ui.editregisterpet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.domain.Pet
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.pettracker.ui.common.Mapper
import com.teammovil.pettracker.ui.common.PetView
import com.teammovil.domain.Error
import com.teammovil.domain.Result
import com.teammovil.usecases.common.UseCaseErrors
import com.teammovil.usecases.editpet.EditPetUseCase
import com.teammovil.usecases.registerpet.RegisterPetUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditRegisterPetViewModel(
    var editPetUseCase: EditPetUseCase,
    var registerPetUseCase: RegisterPetUseCase
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
        _petView.value = pet
        savePet(Mapper.mapPet(pet))
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
            val result =
                if (pet.id.isEmpty())
                    withContext(Dispatchers.IO) { registerPetUseCase.invoke(pet) }
                else
                    withContext(Dispatchers.IO) { editPetUseCase.invoke(pet) }
            _model.value = UiModel.Loading(false)
            manageResult(result)
        }
    }

    private fun getPet (id: String){
        viewModelScope.launch {
            /*_model.value = UiModel.Loading(true)
            val result = withContext(Dispatchers.IO){petRepository.getPetById(id)}
            _model.value = UiModel.Loading(false)
            if(result!=null)
                _petView.value = Mapper.mapPet(result)*/
        }
    }

    private fun manageResult (result: Result<Unit, List<Error>>){
        if(result.valid){
            showSuccessAdvice()
        }
        else{
            when{
                result.error.isNullOrEmpty() -> {}
                result.error!![0].code == UseCaseErrors.REGISTER_PET_GENERIC_ERROR -> {showRegistrationError()}
                else -> { showPetViewErrors(result.error!!) }
            }
        }
    }

    private fun showSuccessAdvice (){
        _model.value = UiModel.SuccessAdvice()
    }

    private fun showRegistrationError (){
        _model.value = UiModel.ErrorAdvice("Hubo un error al registrar su mascota. Intente más tarde.")
    }

    private fun showPetViewErrors (errorList: List<Error>){
        _petView.value?.let{
            _petView.value = Mapper.map(it, errorList)
        }
    }

    fun onSendEvidence(petId: String) {
        _navigation.value = Event(UiEvents.GoToSendEvidence(petId))
    }

    fun onAssignAdopter (petId: String){
        _navigation.value = Event(UiEvents.GoToAssignAdopter(petId))
    }
}


