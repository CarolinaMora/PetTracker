package com.teammovil.pettracker.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.pettracker.data.adopter.AdopterRepository
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.pettracker.util.MessageValidation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdopterLoginViewModel(private val adopterRepository: AdopterRepository): ViewModel() {

    sealed class UiModel {
        object Loading : UiModel()
        class LoginError(val adopterView: AdopterView) : UiModel()
        class SuccessNotification(val message: String) : UiModel()
        class ErrorNotification(val message: String) : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> get() = _model

    private val _navigation = MutableLiveData<Event<Unit>>()
    val navigation: LiveData<Event<Unit>> get() = _navigation

    fun onLoginAdopter(adopter: AdopterView){
        if(validateView((adopter))){
            loginAdopter(adopter)
        }else
            _model.value = UiModel.LoginError(adopter)
    }

    fun onClickOkAdvice (){
        _navigation.value = Event(Unit)
    }

    private fun loginAdopter (adopter: AdopterView){
        viewModelScope.launch{
            _model.value = UiModel.Loading

            val result = withContext(Dispatchers.IO){
                val password = ""
                val name = ""
                adopterRepository.login(name, password)
            }
            if(result){
                showSuccessAdvice()
            } else showLoginError()
        }
    }

    private fun validateView(adopter: AdopterView): Boolean {

        var valid = true

        if (adopter.email.value.isNullOrEmpty()) {
            valid = false
            adopter.email.valid = false
            adopter.email.message = MessageValidation.FIELD_REQUIERED
        }

        if (adopter.password.value.isNullOrEmpty()) {
            valid = false
            adopter.password.valid = false
            adopter.password.message = MessageValidation.FIELD_REQUIERED
        }


        return valid
    }

//    private fun mapAdopter (origin: AdopterView): Adopter{
//        return Adopter(
//            origin.email.value!!,
//            origin.password.value!!
//
//        )
//    }

    private fun showSuccessAdvice () {
        _model.value = UiModel.SuccessNotification(MessageValidation.ADOPTER_LOGIN_SUCCESS)
    }
    private fun showLoginError () {
        _model.value = UiModel.ErrorNotification(MessageValidation.ADOPTER_RESGISTER_FAILURE)
    }
}