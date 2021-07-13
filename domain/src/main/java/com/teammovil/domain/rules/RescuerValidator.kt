package com.teammovil.domain.rules

import com.teammovil.domain.Rescuer
import com.teammovil.domain.Result
import com.teammovil.domain.Error

interface RescuerValidator {
    fun validateRescuer (rescuer: Rescuer): Result<Unit, List<Error>>
}

class RescuerValidatorImpl : RescuerValidator {

    override fun validateRescuer (rescuer: Rescuer): Result<Unit, List<Error>>{
        val errorList = mutableListOf<Error>()
        if (rescuer.name.isEmpty()) {
            errorList.add(Error(RulesErrors.NAME_FIELD_EMPTY_ERROR))
        }

        if (rescuer.activityStartDate == null) {
            errorList.add(Error(RulesErrors.ACTIVITY_START_DATE_FIELD_EMPTY_ERROR))
        }

        if (rescuer.email.isEmpty()) {
            errorList.add(Error(RulesErrors.EMAIL_FIELD_EMPTY_ERROR))
        }

        if (rescuer.password.isEmpty()) {
            errorList.add(Error(RulesErrors.PASSWORD_FIELD_EMPTY_ERROR))
        }

        if (rescuer.phone.isEmpty()) {
            errorList.add(Error(RulesErrors.PHONE_FIELD_EMPTY_ERROR))
        }

        if (rescuer.address.isNullOrEmpty()) {
            errorList.add(Error(RulesErrors.ADDRESS_FIELD_EMPTY_ERROR))
        }

        if (rescuer.description.isEmpty()) {
            errorList.add(Error(RulesErrors.DESCRIPTION_FIELD_EMPTY_ERROR))
        }

        return if(errorList.isEmpty())
            Result(Unit, null)
        else
            Result(null, errorList)
    }
}