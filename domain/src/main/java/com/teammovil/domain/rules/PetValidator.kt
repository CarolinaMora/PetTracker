package com.teammovil.domain.rules

import com.teammovil.domain.*

object PetValidator {

    fun validatePet (pet: Pet): Result<Unit, List<Error>>{
        val errorList = mutableListOf<Error>()
        if (pet.name.isEmpty()){
            errorList.add(Error(RulesErrors.NAME_FIELD_EMPTY_ERROR))
        }
        if (pet.description.isEmpty()){
            errorList.add(Error(RulesErrors.DESCRIPTION_FIELD_EMPTY_ERROR))
        }
        if (pet.race.isEmpty()) {
            errorList.add(Error(RulesErrors.RACE_FIELD_EMPTY_ERROR))
        }
        if (pet.gender == GenderType.UNKNOWN){
            errorList.add(Error(RulesErrors.GENDER_FIELD_EMPTY_ERROR))
        }
        if(pet.petType == PetType.UNKNOWN){
            errorList.add(Error(RulesErrors.TYPE_FIELD_EMPTY_ERROR))
        }
        if(pet.approximateDateOfBirth == null){
            errorList.add(Error(RulesErrors.BIRTH_DATE_FIELD_EMPTY_ERROR))
        }
        if(pet.rescueDate == null){
            errorList.add(Error(RulesErrors.RESCUE_DATE_FIELD_EMPTY_ERROR))
        }
        if(pet.mainPhoto.isEmpty()){
            errorList.add(Error(RulesErrors.MAIN_PHOTO_FIELD_EMPTY_ERROR))
        }

        return if(errorList.isEmpty())
            Result(Unit, null)
        else
            Result(null, errorList)
    }
}