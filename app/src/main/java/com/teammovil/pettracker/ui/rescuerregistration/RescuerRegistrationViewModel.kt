package com.teammovil.pettracker.ui.rescuerregistration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.domain.Error
import com.teammovil.domain.Result
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.pettracker.ui.common.Mapper
import com.teammovil.pettracker.util.MessageValidation
import com.teammovil.usecases.common.UseCaseErrors
import com.teammovil.usecases.registerrescuer.RegisterRescuerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RescuerRegistrationViewModel @Inject constructor(private val registerRescuerUseCase: RegisterRescuerUseCase) : ViewModel() {

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
        saveRescuer(rescuer)
    }

    fun onClickOkAdvice (){
        _navigation.value = Event(Unit)
    }

    private fun saveRescuer (rescuer: RescuerView){
        viewModelScope.launch {
            _model.value = UiModel.Loading
            val result = withContext(Dispatchers.IO){registerRescuerUseCase.invoke(Mapper.map(rescuer))}
            manageResult(result, rescuer)
        }
    }


    private fun manageResult (result: Result<Unit, List<Error>>, rescuer: RescuerView){
        if(result.valid)
            showSuccessAdvice()
        else {
            when{
                result.error.isNullOrEmpty() -> {}
                result.error!![0].code == UseCaseErrors.REGISTER_RESCUER_GENERIC_ERROR -> {showRegistrationError()}
                else -> { showPetViewErrors(result.error!!, rescuer) }
            }
        }
    }

    private fun showSuccessAdvice (){
        _model.value = UiModel.SuccessNotification(MessageValidation.RESCUER_REGISTER_SUCCESS)
    }

    private fun showRegistrationError (){
        _model.value = UiModel.ErrorNotification(MessageValidation.RESCUER_REGISTER_FAILURE)
    }

    private fun showPetViewErrors(errorList: List<Error>, rescuerView: RescuerView){
        _model.value = UiModel.RescuerError(Mapper.map(rescuerView, errorList))
    }

}