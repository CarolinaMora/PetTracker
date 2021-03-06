package com.teammovil.pettracker.ui.rescuerlogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.domain.Error
import com.teammovil.domain.Result
import com.teammovil.pettracker.R
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.pettracker.ui.common.Mapper
import com.teammovil.pettracker.ui.common.UserView
import com.teammovil.pettracker.ui.rescuerregistration.RescuerView
import com.teammovil.pettracker.util.MessageValidation
import com.teammovil.usecases.common.UseCaseErrors
import com.teammovil.usecases.loginrescuer.LoginRescuerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class RescuerLoginViewModel @Inject constructor(private val getRescuerUseCase: LoginRescuerUseCase): ViewModel() {

    sealed class UiModel {
        class Loading(val show: Boolean) : UiModel()
        class RescuerError(val rescuerView: UserView) : UiModel()
        class ErrorNotification(val message: Int) : UiModel()
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
            _model.value = UiModel.Loading(true)
            val result = withContext(Dispatchers.IO){
                getRescuerUseCase.invoke(user.email.value!!, user.password.value!!)}
            _model.value = UiModel.Loading(false)
            validateView(result, user)
        }
    }

    private fun validateView(result: Result<Unit, List<Error>>, rescuer: UserView) {
        if(result.valid)
            navigateToHome()
        else {
            when{
                result.error.isNullOrEmpty() -> {}
                result.error!![0].code == UseCaseErrors.LOGIN_RESCUER_ERROR ->{showLoginError()}
                else -> {rescuerErrors(result.error!!, rescuer) }
            }
        }

    }

    private fun showLoginError () {
        _model.value = UiModel.ErrorNotification(R.string.failure_login)
    }
    private fun rescuerErrors(errorList: List<Error>, user: UserView){
        _model.value = UiModel.RescuerError(Mapper.map(user, errorList))
    }

    private fun navigateToHome (){
        _navigation.value = Event(UiNavigation.GoHome)
    }

    private fun navigateToRegistration() {
        _navigation.value = Event(UiNavigation.GoRegister)
    }

}
