package com.teammovil.pettracker.ui.common

import com.teammovil.pettracker.R
import com.teammovil.pettracker.getDateFromString

object Validations {

    fun validateView(pet: PetView): Boolean {
        var valid = true
        if (pet.name.value.isNullOrEmpty()){
            valid = false
            pet.name.valid = false
            pet.name.messageResourceId = R.string.error_field_required
        }
        if (pet.description.value.isNullOrEmpty()){
            valid = false
            pet.description.valid = false
            pet.description.messageResourceId = R.string.error_field_required
        }
        if (pet.race.value.isNullOrEmpty()) {
            valid = false
            pet.race.valid = false
            pet.race.messageResourceId = R.string.error_field_required
        }
        if (pet.gender.value.isNullOrEmpty() || pet.gender.selection == 0){
            valid = false
            pet.gender.valid = false
            pet.gender.messageResourceId = R.string.prompt_select_option
        }
        if(pet.petType.value.isNullOrEmpty() || pet.petType.selection == 0){
            valid = false
            pet.petType.valid = false
            pet.petType.messageResourceId = R.string.prompt_select_option
        }
        if(pet.approximateDateOfBirth.value == null || getDateFromString(pet.approximateDateOfBirth.value) == null){
            valid = false
            pet.approximateDateOfBirth.valid = false
            pet.approximateDateOfBirth.messageResourceId = R.string.error_field_required
        }
        if(pet.rescueDate.value == null || getDateFromString(pet.rescueDate.value) == null){
            valid = false
            pet.rescueDate.valid = false
            pet.rescueDate.messageResourceId = R.string.error_field_required
        }
        if(pet.mainPhoto.value.isNullOrEmpty()){
            valid = false
            pet.mainPhoto.valid = false
            pet.mainPhoto.messageResourceId = R.string.error_photo_required
        }
        return valid
    }
}