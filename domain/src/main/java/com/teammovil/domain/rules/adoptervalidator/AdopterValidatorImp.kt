package com.teammovil.domain.rules.adoptervalidator

import com.teammovil.domain.Adopter
import com.teammovil.domain.Error
import com.teammovil.domain.GenderType
import com.teammovil.domain.Result
import com.teammovil.domain.rules.RulesErrors

class AdopterValidatorImp: AdopterValidator {
   override fun validateAdopter(adopter: Adopter): Result<Unit, List<Error>> {

        val errorList = mutableListOf<Error>()
        if(adopter.name.isEmpty()){
            errorList.add(Error(RulesErrors.NAME_FIELD_EMPTY_ERROR))
        }
        if(adopter.firstLastName.isEmpty()){
            errorList.add(Error(RulesErrors.FIRST_LAST_NAME_FIELD_EMPTY_ERROR))
        }
        if (adopter.secondLastName.isEmpty()){
            errorList.add(Error(RulesErrors.SECOND_LAST_NAME_FIELD_EMPTY_ERROR))
        }
        if (adopter.gender == GenderType.UNKNOWN){
            errorList.add(Error(RulesErrors.GENDER_FIELD_EMPTY_ERROR))
        }
        if (adopter.BirthDate == null){
            errorList.add(Error(RulesErrors.BIRTH_DATE_FIELD_EMPTY_ERROR))
        }
        if(adopter.email.isEmpty()){
            errorList.add(Error(RulesErrors.EMAIL_FIELD_EMPTY_ERROR))
        }
        if (adopter.password.isEmpty()){
            errorList.add(Error(RulesErrors.PASSWORD_FIELD_EMPTY_ERROR))
        }
        if(adopter.phone.isEmpty()){
            errorList.add(Error(RulesErrors.PHONE_FIELD_EMPTY_ERROR))
        }
        if (adopter.address.isEmpty()){
            errorList.add(Error(RulesErrors.ADDRESS_FIELD_EMPTY_ERROR))
        }
        return if(errorList.isEmpty()){
            Result(Unit,null)
        }else{
            Result(null,errorList)
        }

    }
}