package com.teammovil.usecases.loginadopter

import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.domain.Error
import com.teammovil.domain.Result
import com.teammovil.domain.rules.UserValidator
import com.teammovil.domain.rules.UserValidatorImpl
import com.teammovil.usecases.common.UseCaseErrors

class LoginAdopterUseCase(
    val adopterRepository: AdopterRepository) : UserValidator by UserValidatorImpl() {

    suspend fun invoke(user: String, password: String): Result<Unit, List<Error>>{
        val loginResultValidation = userValidator(user, password)
        if (loginResultValidation.valid){
            val success = adopterRepository.login(user, password)
            if (success)
                return Result(Unit, null)
            else
                return Result(null, listOf(Error(UseCaseErrors.LOGIN_ADOPTER_GENERIC_ERROR)))
        }else{
            return Result(null, loginResultValidation.error)
        }

    }
}