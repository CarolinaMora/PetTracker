package com.teammovil.pettracker.ui.editregisterpet

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.data.pet.PetRepository
import com.teammovil.domain.Error
import com.teammovil.domain.Pet
import com.teammovil.domain.Result
import com.teammovil.pettracker.R
import com.teammovil.pettracker.data.services.PetExternalDataAccessServiceImpl
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.pettracker.ui.common.Mapper
import com.teammovil.pettracker.ui.common.PetView
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
        class ErrorAdvice (@StringRes val messageResourceId: Int): UiModel()
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
            //TODO: Cambiar por caso de uso que obtiene el Pet
            val petRepository = PetRepository(PetExternalDataAccessServiceImpl())
            _model.value = UiModel.Loading(true)
            val result = withContext(Dispatchers.IO){petRepository.getPetById(id)}
            _model.value = UiModel.Loading(false)
            if(result!=null)
                _petView.value = Mapper.mapPet(result)
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
                result.error!![0].code == UseCaseErrors.EDIT_PET_GENERIC_ERROR -> {showModificationError()}
                else -> { showPetViewErrors(result.error!!) }
            }
        }
    }

    private fun showSuccessAdvice (){
        _model.value = UiModel.SuccessAdvice()
    }

    private fun showRegistrationError (){
        _model.value = UiModel.ErrorAdvice(R.string.alert_message_registration_error)
    }

    private fun showModificationError (){
        _model.value = UiModel.ErrorAdvice(R.string.alert_message_edit_error)
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


