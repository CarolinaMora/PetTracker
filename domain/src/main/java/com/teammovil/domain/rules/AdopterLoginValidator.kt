package com.teammovil.domain.rules

import com.teammovil.domain.Error
import com.teammovil.domain.Result

object AdopterLoginValidator {
    fun validateAdopterLogin(user: String, password: String): Result<Unit, List<Error>> {

        val errorList = mutableListOf<Error>()
        if (user.isEmpty()) {
            errorList.add(Error(RulesErrors.EMAIL_FIELD_EMPTY_ERROR))
        }
        if (password.isEmpty()) {
            errorList.add(Error(RulesErrors.PASSWORD_FIELD_EMPTY_ERROR))
        }
        return if (errorList.isEmpty()) {
            Result(Unit, null)
        } else {
            Result(null, errorList)
        }
    }
}