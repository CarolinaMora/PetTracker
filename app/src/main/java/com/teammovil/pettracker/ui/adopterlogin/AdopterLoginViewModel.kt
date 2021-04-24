package com.teammovil.pettracker.ui.adopterlogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.pettracker.R
import com.teammovil.pettracker.data.adopter.AdopterRepository
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.pettracker.ui.common.UserView
import com.teammovil.pettracker.util.MessageValidation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdopterLoginViewModel(private val adopterRepository: AdopterRepository): ViewModel() {

    sealed class UiModel {
        object Loading : UiModel()
        class LoginError(val userView: UserView) : UiModel()
        class ErrorNotification(val message: String) : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> get() = _model

    private val _navigation = MutableLiveData<Event<Unit>>()
    val navigation: LiveData<Event<Unit>> get() = _navigation

    fun onLoginAdopter(user: UserView){
        if(validateView((user))){
            loginAdopter(user)
        }else
            _model.value = UiModel.LoginError(user)
    }

    private fun loginAdopter (user: UserView){
        viewModelScope.launch{
            _model.value = UiModel.Loading

            val result = withContext(Dispatchers.IO){
                adopterRepository.login(user.email.value!!, user.password.value!!)
            }
            if(result){
                navigateTo()
            } else showLoginError()
        }
    }

    private fun validateView(user: UserView): Boolean {

        var valid = true

        if (user.email.value.isNullOrEmpty()) {
            valid = false
            user.email.valid = false
            user.email.messageResourceId = R.string.error_field_required
        }

        if (user.password.value.isNullOrEmpty()) {
            valid = false
            user.password.valid = false
            user.password.messageResourceId = R.string.error_field_required
        }


        return valid
    }

    private fun navigateTo (){
        _navigation.value = Event(Unit)
    }

    private fun showLoginError () {
        _model.value = UiModel.ErrorNotification(MessageValidation.ADOPTER_RESGISTER_FAILURE)
    }
}