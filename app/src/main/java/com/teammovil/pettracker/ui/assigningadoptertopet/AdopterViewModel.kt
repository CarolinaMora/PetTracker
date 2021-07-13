package com.teammovil.pettracker.ui.assigningadoptertopet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.pettracker.ui.common.ScopedViewModel
import com.teammovil.pettracker.util.MessageValidation
import com.teammovil.usecases.assignadoptertopet.AssignAdopterToPetUseCase
import com.teammovil.usecases.getalladopters.GetAllAdoptersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdopterViewModel @Inject constructor(
    var assignAdopterToPetUseCase: AssignAdopterToPetUseCase,
    var getAllAdoptersUseCase: GetAllAdoptersUseCase,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    sealed class UiModel {
        object Loading : UiModel()
        data class AdoptersContent(val adopter: List<com.teammovil.domain.Adopter>) : UiModel()
        data class SuccessNotification(val message: String) : UiModel()
        data class ErrorNotification(val message: String) : UiModel()
        data class SuccessConfimation(val adopterId: String, val adopterName: String) : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> get() = _model

    private val _navigation = MutableLiveData<Event<Unit>>()
    val navigation: LiveData<Event<Unit>> get() = _navigation

    fun onStartView() {
        launch {
            _model.value = UiModel.Loading
            val result =  getAllAdoptersUseCase.invoke()
            setView(result)
        }
    }

    fun onClickOkAdvice() {
        _navigation.value = Event(Unit)
    }

    fun onAdopterClicked(adopter: com.teammovil.domain.Adopter) {
        showConfirmation(adopter.email, adopter.name)
    }

    private fun showConfirmation(adopterId: String, adopterName: String) {
        _model.value = UiModel.SuccessConfimation(adopterId, adopterName)
    }

    private fun showSuccessAdvice() {
        _model.value = UiModel.SuccessNotification(MessageValidation.ADOPTER_REGISTER_SUCCESS)
    }

    private fun showRegistrationError() {
        _model.value = UiModel.ErrorNotification(MessageValidation.ADOPTER_REGISTER_FAILURE)
    }

    private fun setView(adopterList: List<com.teammovil.domain.Adopter>) {
        _model.value = UiModel.AdoptersContent(adopterList)
    }

    fun onClickConfirmation(petId: String, adopterId: String) {
        onAssignAdopterToPet(adopterId, petId)
    }

    fun onAssignAdopterToPet(adopterId: String, petId: String) {
        launch {
            _model.value = UiModel.Loading
            val result = assignAdopterToPetUseCase.invoke(petId, adopterId)
            if (result) {
                showSuccessAdvice()
            } else {
                showRegistrationError()
            }
        }
    }

}