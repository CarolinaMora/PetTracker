package com.teammovil.pettracker.ui.assigningadoptertopet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.pettracker.data.adopter.AdopterRepository
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.domain.Adopter
import com.teammovil.pettracker.ui.adopterregistration.AdopterRegistrationViewModel
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.pettracker.util.MessageValidation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdopterViewModel(val adopterRepository: AdopterRepository,val petRepository: PetRepository):ViewModel() {

    sealed class UiModel{
        object Loading: UiModel()
        class AdoptersContent(val adopter: List<Adopter>) :UiModel()
        class SuccessNotification(val message: String) : UiModel()
        class ErrorNotification(val message: String) : UiModel()
        class SuccessConfimation(val adopterId: String, val adopterName: String): UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> get() = _model

    private val _navigation = MutableLiveData<Event<Unit>>()
    val navigation: LiveData<Event<Unit>> get() = _navigation

    init {
        viewModelScope.launch {
            _model.value = UiModel.Loading
            var result = withContext(Dispatchers.IO){adopterRepository.getAllAdopters()}
            setView(result)
        }
    }

    fun onClickOkAdvice (){
        _navigation.value = Event(Unit)
    }

    fun onAdopterClicked(adopter: Adopter){
        showConfirmation(adopter.id,adopter.name)
    }

    private fun showConfirmation (adopterId: String, adopterName: String){
        _model.value = UiModel.SuccessConfimation(adopterId,adopterName)
    }

    private fun showSuccessAdvice (){
        _model.value = UiModel.SuccessNotification(MessageValidation.ADOPTER_REGISTER_SUCCESS)
    }

    private fun showRegistrationError (){
        _model.value = UiModel.ErrorNotification(MessageValidation.ADOPTER_REGISTER_FAILURE)
    }

    private fun setView(adopterList: List<Adopter>){
        _model.value = UiModel.AdoptersContent(adopterList)
    }

    fun onClickConfirmation(petId: String, adopterId: String) {
        onAssignAdopterToPet(adopterId,petId)
    }

    fun onAssignAdopterToPet(adopterId: String, petId: String){
        _model.value = UiModel.Loading
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                petRepository.assignAdopterToPet(petId,adopterId)
            }
            if(result){
                showSuccessAdvice()
            }else {
                showRegistrationError()
            }
        }
    }

}