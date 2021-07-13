package com.teammovil.pettracker.ui.editregisterpet

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teammovil.domain.Error
import com.teammovil.domain.Pet
import com.teammovil.domain.Result
import com.teammovil.pettracker.R
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.pettracker.ui.common.Mapper
import com.teammovil.pettracker.ui.common.PetView
import com.teammovil.pettracker.ui.common.ScopedViewModel
import com.teammovil.usecases.common.UseCaseErrors
import com.teammovil.usecases.editpet.EditPetUseCase
import com.teammovil.usecases.petdetail.GetPetUseCase
import com.teammovil.usecases.registerpet.RegisterPetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditRegisterPetViewModel @Inject constructor(
    var editPetUseCase: EditPetUseCase,
    var getPetUseCase: GetPetUseCase,
    var registerPetUseCase: RegisterPetUseCase,
    uiDispatcher: CoroutineDispatcher
): ScopedViewModel(uiDispatcher) {

    sealed class UiModel {
        data class Loading(val show: Boolean): UiModel()
        object SuccessAdvice : UiModel()
        data class ErrorAdvice (@StringRes val messageResourceId: Int): UiModel()
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
        launch {
            _model.value = UiModel.Loading(true)
            val result =
                if (pet.id.isEmpty())
                    registerPetUseCase.invoke(pet)
                else
                    editPetUseCase.invoke(pet)
            _model.value = UiModel.Loading(false)
            manageResult(result)
        }
    }

    private fun getPet (id: String){
        launch {
            _model.value = UiModel.Loading(true)
            val result = getPetUseCase.invoke(id)
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
        _model.value = UiModel.SuccessAdvice
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


