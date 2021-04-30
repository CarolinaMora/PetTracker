package com.teammovil.pettracker.ui.rescuerlogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.pettracker.data.adopter.AdopterRepository
import com.teammovil.pettracker.data.rescuer.RescuerRepository
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.pettracker.ui.common.UserView
import com.teammovil.pettracker.util.MessageValidation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RescuerLoginViewModel(private val rescuerRepository: RescuerRepository): ViewModel() {

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

    fun onLoginAdopter(user: UserView){
        if(validateView((user))){
            loginAdopter(user)
        }else
            _model.value = UiModel.LoginError(user)
    }

    fun onRegisterRescuer (){
        navigateToRegistration()
    }

    private fun loginAdopter (user: UserView){
        viewModelScope.launch{
            _model.value = UiModel.Loading

            val result = withContext(Dispatchers.IO){
                rescuerRepository.login(user.email.value!!, user.password.value!!)
            }
            if(result){
                navigateToHome()
            } else showLoginError()
        }
    }

    private fun validateView(user: UserView): Boolean {

        var valid = true

        if (user.email.value.isNullOrEmpty()) {
            valid = false
            user.email.valid = false
            user.email.message = MessageValidation.FIELD_REQUIERED
        }

        if (user.password.value.isNullOrEmpty()) {
            valid = false
            user.password.valid = false
            user.password.message = MessageValidation.FIELD_REQUIERED
        }


        return valid
    }

    private fun showLoginError () {
        _model.value = UiModel.ErrorNotification(MessageValidation.ADOPTER_RESGISTER_FAILURE)
    }

    private fun navigateToHome (){
        _navigation.value = Event(UiNavigation.GoHome)
    }

    private fun navigateToRegistration() {
        _navigation.value = Event(UiNavigation.GoRegister)
    }

}