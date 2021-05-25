package com.teammovil.pettracker.ui.adopterlogin

import android.text.BoringLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.teammovil.pettracker.ui.common.Event
import com.teammovil.pettracker.ui.common.UserView

import com.teammovil.pettracker.util.MessageValidation

import com.teammovil.usecases.loginadopter.LoginAdopterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AdopterLoginViewModel @Inject constructor(private val getAdopterUseCase: LoginAdopterUseCase): ViewModel() {

    sealed class UiModel {
        class Loading(val show : Boolean) : UiModel()
        class LoginError(val adopterView: UserView) : UiModel()
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

    fun onLoginAdopter(user: UserView){
        loginAdopter(user)
    }

    fun onRegisterAdopter (){
        navigateToRegistration()
    }

    private fun loginAdopter (user: UserView){
        viewModelScope.launch{
            _model.value = UiModel.Loading(true)
            val result = withContext(Dispatchers.IO){
                getAdopterUseCase.invoke(user.email.value!!, user.password.value!!)}
            _model.value = UiModel.Loading(false)
            validateView(result)
        }
    }

    private fun validateView(result: Boolean) {
        val valid = true
        if( result == valid) navigateToHome() else{ showLoginError() }
    }

    private fun navigateToHome (){
        _navigation.value = Event(UiNavigation.GoHome)
    }

    private fun navigateToRegistration (){
        _navigation.value = Event (UiNavigation.GoRegister)
    }

    private fun showLoginError () {

        _model.value = UiModel.ErrorNotification(MessageValidation.LOGING_FAILURE)
    }
}