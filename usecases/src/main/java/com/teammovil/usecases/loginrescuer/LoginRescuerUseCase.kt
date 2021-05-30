package com.teammovil.usecases.loginrescuer

import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.domain.Error

import com.teammovil.domain.Result
import com.teammovil.domain.rules.RescuerLoginValidator

import com.teammovil.usecases.common.UseCaseErrors

class LoginRescuerUseCase( val rescuerRepository: RescuerRepository) {

    suspend fun invoke(user: String, password: String): Result<Unit, List<Error>>{
        val loginRescuerValidation = RescuerLoginValidator.validateRescuer(user, password)
        return if(loginRescuerValidation.valid){
            val loginRescuerSuccess = rescuerRepository.login(user, password)
            if(loginRescuerSuccess)
                Result(Unit, null)
            else
                Result(null, listOf(Error(UseCaseErrors.LOGIN_RESCUER_ERROR)))
        } else
            Result(null, loginRescuerValidation.error)

    }
//    suspend fun invoke(user: String, password: String) = rescuerRepository.login(user, password)
}
