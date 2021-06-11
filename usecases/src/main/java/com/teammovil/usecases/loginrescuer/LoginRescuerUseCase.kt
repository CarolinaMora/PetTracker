package com.teammovil.usecases.loginrescuer

import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.domain.Error

import com.teammovil.domain.Result
import com.teammovil.domain.rules.UserValidator
import com.teammovil.domain.rules.UserValidatorImpl

import com.teammovil.usecases.common.UseCaseErrors

class LoginRescuerUseCase( val rescuerRepository: RescuerRepository
): UserValidator by UserValidatorImpl() {

    suspend fun invoke(user: String?, password: String?): Result<Unit, List<Error>>{
        val loginRescuerValidation = userValidator(user, password)
        return if(loginRescuerValidation.valid){
            val loginRescuerSuccess = rescuerRepository.login(user!!, password!!)
            if(loginRescuerSuccess)
                Result(Unit, null)
            else
                Result(null, listOf(Error(UseCaseErrors.LOGIN_RESCUER_ERROR)))
        } else
            Result(null, loginRescuerValidation.error)

    }

}
