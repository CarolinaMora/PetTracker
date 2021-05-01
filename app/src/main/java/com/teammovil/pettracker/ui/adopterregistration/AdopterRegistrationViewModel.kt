package com.teammovil.pettracker.ui.adopterregistration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.pettracker.data.adopter.AdopterRepository
import com.teammovil.pettracker.domain.Adopter
import com.teammovil.pettracker.domain.GenderType
import com.teammovil.pettracker.getDateFromString
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.pettracker.util.MessageValidation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdopterRegistrationViewModel(val adopterRepository: AdopterRepository) : ViewModel() {

    sealed class UiModel {
        object Loading : UiModel()
        class AdopterError(val adopterView: AdopterView) : UiModel()
        class SuccessNotification(val message: String) : UiModel()
        class ErrorNotification(val message: String) : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> get() = _model

    private val _navigation = MutableLiveData<Event<Unit>>()
    val navigation: LiveData<Event<Unit>> get() = _navigation

    fun onSaveAdopter (adopter: AdopterView){
        if(validateView(adopter)){
            saveAdopter(mapAdopter(adopter))
        }
        else{
            _model.value = UiModel.AdopterError(adopter)
        }
    }

    fun onClickOkAdvice (){
        _navigation.value = Event(Unit)
    }

    private fun saveAdopter (adopter: Adopter){
        viewModelScope.launch {
            _model.value = UiModel.Loading
            val result = withContext(Dispatchers.IO){adopterRepository.saveAdopter(adopter)}
            if(result) showSuccessAdvice()
            else showRegistrationError()
        }
    }

    private fun validateView(adopter: AdopterView): Boolean {
        var valid = true
        if (adopter.name.value.isNullOrEmpty()) {
            valid = false
            adopter.name.valid = false
            adopter.name.message = MessageValidation.FIELD_REQUIRED
        }

        if (adopter.firstLastName.value.isNullOrEmpty()) {
            valid = false
            adopter.firstLastName.valid = false
            adopter.firstLastName.message = MessageValidation.FIELD_REQUIRED
        }

        if (adopter.secondLastName.value.isNullOrEmpty()) {
            valid = false
            adopter.secondLastName.valid = false
            adopter.secondLastName.message = MessageValidation.FIELD_REQUIRED
        }

        if (adopter.genderType.value.isNullOrEmpty() || adopter.genderType.value == MessageValidation.SELECT_GENDER) {
            valid = false
            adopter.genderType.valid = false
            adopter.genderType.message = MessageValidation.SELECT_FIELD_VALID
        }

        if (adopter.birthDay.value == null || getDateFromString(adopter.birthDay.value) == null) {
            valid = false
            adopter.birthDay.valid = false
            adopter.birthDay.message = MessageValidation.FIELD_REQUIRED
        }

        if (adopter.email.value.isNullOrEmpty()) {
            valid = false
            adopter.email.valid = false
            adopter.email.message = MessageValidation.FIELD_REQUIRED
        }

        if (adopter.password.value.isNullOrEmpty()) {
            valid = false
            adopter.password.valid = false
            adopter.password.message = MessageValidation.FIELD_REQUIRED
        }

        if (adopter.phone.value.isNullOrEmpty()) {
            valid = false
            adopter.phone.valid = false
            adopter.phone.message = MessageValidation.FIELD_REQUIRED
        }

        if (adopter.address.value.isNullOrEmpty()) {
            valid = false
            adopter.address.valid = false
            adopter.address.message = MessageValidation.FIELD_REQUIRED
        }
        return valid
    }

    private fun mapAdopter (origin: AdopterView): Adopter{
        return Adopter(
            origin.email.value!!,
            origin.name.value!!,
            origin.firstLastName.value!!,
            origin.secondLastName.value!!,
            GenderType.valueOf(origin.genderType.value!!),
            getDateFromString(origin.birthDay.value!!)!!,
            origin.password.value!!,
            origin.phone.value!!,
            origin.address.value!!
        )
    }

    private fun showSuccessAdvice (){
        _model.value = UiModel.SuccessNotification(MessageValidation.ADOPTER_REGISTER_SUCCESS)
    }

    private fun showRegistrationError (){
        _model.value = UiModel.ErrorNotification(MessageValidation.ADOPTER_REGISTER_FAILURE)
    }

}