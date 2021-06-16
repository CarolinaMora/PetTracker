package com.teammovil.pettracker.ui.adopterregistration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teammovil.domain.Error
import com.teammovil.domain.Result
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.pettracker.ui.common.Mapper
import com.teammovil.pettracker.ui.common.ScopedViewModel
import com.teammovil.pettracker.util.MessageValidation
import com.teammovil.usecases.common.UseCaseErrors
import com.teammovil.usecases.registeradopter.RegisterAdopterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AdopterRegistrationViewModel @Inject constructor(
    val registerAdopterUseCase: RegisterAdopterUseCase,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    sealed class UiModel {
        object Loading : UiModel()
        class AdopterError(val adopterView: AdopterView) : UiModel()
        data class SuccessNotification(val message: String) : UiModel()
        class ErrorNotification(val message: String) : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> get() = _model

    private val _navigation = MutableLiveData<Event<Unit>>()
    val navigation: LiveData<Event<Unit>> get() = _navigation

    fun onSaveAdopter(adopter: AdopterView) {
        saveAdopter(adopter)
    }

    fun onClickOkAdvice() {
        _navigation.value = Event(Unit)
    }

    private fun saveAdopter(adopter: AdopterView) {
        launch {
            _model.value = UiModel.Loading
            val result = withContext(Dispatchers.IO) { registerAdopterUseCase.invoke(Mapper.map(adopter)) }
            manageResult(result, adopter)
        }
    }

    private fun manageResult(result: Result<Unit, List<Error>>, adopter: AdopterView) {
        if (result.valid) {
            showSuccessAdvice()
        } else {
            when {
                result.error.isNullOrEmpty() -> {
                }
                result.error!![0].code == UseCaseErrors.REGISTER_ADOPTER_GENERIC_ERROR -> {
                    showRegistrationError()
                }
                else -> {
                    showAdopterViewErrors(result.error!!, adopter)
                }
            }
        }
    }

    private fun showSuccessAdvice() {
        _model.value = UiModel.SuccessNotification(MessageValidation.ADOPTER_REGISTER_SUCCESS)
    }

    private fun showRegistrationError() {
        _model.value = UiModel.ErrorNotification(MessageValidation.ADOPTER_REGISTER_FAILURE)
    }

    private fun showAdopterViewErrors(errorList: List<Error>, adopterView: AdopterView) {
        _model.value = UiModel.AdopterError(Mapper.map(adopterView, errorList))
    }

}