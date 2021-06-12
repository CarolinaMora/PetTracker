package com.teammovil.domain.rules

import com.teammovil.domain.*

interface UserValidator {
    fun validateUser(user: String?, password: String?): Result<Unit, List<Error>>
}

class UserValidatorImpl : UserValidator {
    override fun validateUser(user: String?, password: String?): Result<Unit, List<Error>> {

        val errorList = mutableListOf<Error>()

        if(user.isNullOrEmpty()){
            errorList.add(Error(RulesErrors.EMAIL_FIELD_EMPTY_ERROR))
        }
        if (password.isNullOrEmpty()){
            errorList.add(Error(RulesErrors.PASSWORD_FIELD_EMPTY_ERROR))
        }
        return if(errorList.isEmpty()){
            Result(Unit,null)
        }else{
            Result(null,errorList)
        }

    }
}
