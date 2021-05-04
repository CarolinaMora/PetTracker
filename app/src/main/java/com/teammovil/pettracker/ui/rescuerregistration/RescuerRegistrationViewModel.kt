package com.teammovil.pettracker.ui.rescuerregistration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.pettracker.getDateFromString
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.pettracker.util.MessageValidation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class RescuerRegistrationViewModel(val rescuerRepository: RescuerRepository) : ViewModel() {

    sealed class UiModel {
        object Loading : UiModel()
        class RescuerError(val rescuerView: RescuerView) : UiModel()
        class SuccessNotification(val message: String) : UiModel()
        class ErrorNotification(val message: String) : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> get() = _model

    private val _navigation = MutableLiveData<Event<Unit>>()
    val navigation: LiveData<Event<Unit>> get() = _navigation

    fun onSaveRescuer (rescuer: RescuerView){
        if(validateView(rescuer)){
            saveRescuer(mapRescuer(rescuer))
        }
        else{
            _model.value = UiModel.RescuerError(rescuer)
        }
    }

    fun onClickOkAdvice (){
        _navigation.value = Event(Unit)
    }

    private fun saveRescuer (rescuer: com.teammovil.domain.Rescuer){
        viewModelScope.launch {
            _model.value = UiModel.Loading
            val result = withContext(Dispatchers.IO){rescuerRepository.registerRescuer(rescuer)}
            if(result) showSuccessAdvice()
            else showRegistrationError()
        }
    }

    private fun validateView(rescuer: RescuerView): Boolean {
        var valid = true
        if (rescuer.name.value.isNullOrEmpty()) {
            valid = false
            rescuer.name.valid = false
            rescuer.name.message = MessageValidation.FIELD_REQUIRED
        }

        if (rescuer.activityStartDate.value == null || getDateFromString(rescuer.activityStartDate.value) == null) {
            valid = false
            rescuer.activityStartDate.valid = false
            rescuer.activityStartDate.message = MessageValidation.FIELD_REQUIRED
        }

        if (rescuer.email.value.isNullOrEmpty()) {
            valid = false
            rescuer.email.valid = false
            rescuer.email.message = MessageValidation.FIELD_REQUIRED
        }

        if (rescuer.password.value.isNullOrEmpty()) {
            valid = false
            rescuer.password.valid = false
            rescuer.password.message = MessageValidation.FIELD_REQUIRED
        }

        if (rescuer.phone.value.isNullOrEmpty()) {
            valid = false
            rescuer.phone.valid = false
            rescuer.phone.message = MessageValidation.FIELD_REQUIRED
        }

        if (rescuer.address.value.isNullOrEmpty()) {
            valid = false
            rescuer.address.valid = false
            rescuer.address.message = MessageValidation.FIELD_REQUIRED
        }

        if (rescuer.descripion.value.isNullOrEmpty()) {
            valid = false
            rescuer.descripion.valid = false
            rescuer.descripion.message = MessageValidation.FIELD_REQUIRED
        }
        return valid
    }

    private fun mapRescuer (origin: RescuerView): com.teammovil.domain.Rescuer {
        return com.teammovil.domain.Rescuer(
            "",
            origin.name.value!!,
            origin.descripion.value!!,
            origin.address.value!!,
            origin.email.value!!,
            origin.password.value!!,
            origin.phone.value!!,
            getDateFromString(origin.activityStartDate.value!!) ?: Date()
        )
    }

    private fun showSuccessAdvice (){
        _model.value = UiModel.SuccessNotification(MessageValidation.RESCUER_REGISTER_SUCCESS)
    }

    private fun showRegistrationError (){
        _model.value = UiModel.ErrorNotification(MessageValidation.RESCUER_REGISTER_FAILURE)
    }

}