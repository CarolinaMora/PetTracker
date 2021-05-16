package com.teammovil.pettracker.ui.rescuerlogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.pettracker.R
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.pettracker.ui.common.UserView
import com.teammovil.pettracker.util.MessageValidation
import com.teammovil.usecases.loginrescuer.LoginRescuerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RescuerLoginViewModel(private val getRescuerUseCase: LoginRescuerUseCase): ViewModel() {

    sealed class UiModel {
        object Loading : UiModel()
        class LoginError(val userView: UserView) : UiModel()
        class ErrorNotification(val message: String) : UiModel()
    }

    sealed class UiNavigation {
        object GoHome : UiNavigation()
        object GoRegister : UiNavigation()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> get() = _model

    private val _navigation = MutableLiveData<Event<UiNavigation>>()
    val navigation: LiveData<Event<UiNavigation>> get() = _navigation

    fun onLoginRescuer(user: UserView){
        loginRescuer(user)
    }

    fun onRegisterRescuer (){
        navigateToRegistration()
    }

    private fun loginRescuer (user: UserView){
        viewModelScope.launch{
            _model.value = UiModel.Loading

            val result = withContext(Dispatchers.IO){
                getRescuerUseCase.invoke(user.email.value!!, user.password.value!!)}
            validateView(result)
        }
    }

    private fun validateView(result: Boolean) {
        val valid = true
        if( result == valid) navigateToHome() else{ showLoginError() } }

    private fun showLoginError () {
        _model.value = UiModel.ErrorNotification(MessageValidation.LOGING_FAILURE)
    }

    private fun navigateToHome (){
        _navigation.value = Event(UiNavigation.GoHome)
    }

    private fun navigateToRegistration() {
        _navigation.value = Event(UiNavigation.GoRegister)
    }

}