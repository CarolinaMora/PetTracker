package com.teammovil.pettracker.ui.common

import com.teammovil.domain.*
import com.teammovil.domain.rules.RulesErrors
import com.teammovil.pettracker.R
import com.teammovil.pettracker.getDateFromString
import com.teammovil.pettracker.getStringFromDate
import com.teammovil.pettracker.ui.adopterregistration.AdopterView
import com.teammovil.pettracker.ui.dewormings.DewormingView
import com.teammovil.pettracker.ui.rescuerregistration.RescuerView
import com.teammovil.pettracker.ui.sendevidence.EvidenceView
import com.teammovil.pettracker.ui.vaccines.VaccineView


import java.util.*

object Mapper {

    fun mapVaccineList (vaccineViewList: List<VaccineView>): List<com.teammovil.domain.Vaccine>{
        return vaccineViewList.map {
            mapVaccine(it)
        }
    }

    fun mapVaccineViewList (origin: List<com.teammovil.domain.Vaccine>): List<VaccineView>{
        val vaccineList = listOf<VaccineView>().toMutableList()
        origin.forEachIndexed { index, vaccine ->  vaccineList.add(
            map(vaccine, index))
        }
        return vaccineList
    }

    fun map (origin: com.teammovil.domain.Vaccine, position: Int): VaccineView{
        return VaccineView(
            position,
            origin.id,
            origin.name,
            getStringFromDate(origin.applicationDate ) ?: ""
        )
    }

    fun mapVaccine (vaccineView: VaccineView): com.teammovil.domain.Vaccine {
        return com.teammovil.domain.Vaccine(
            id = vaccineView.idExternal,
            name = vaccineView.name,
            applicationDate = getDateFromString(vaccineView.applicationDate) ?: Date()
        )
    }

    fun mapDewormingList (dewormingViewList: List<DewormingView>): List<com.teammovil.domain.Deworming>{
        return dewormingViewList.map {
            com.teammovil.domain.Deworming(
                it.idExternal,
                it.name,
                getDateFromString(it.applicationDate) ?: Date())
        }
    }

    fun mapDewormingViewList (origin: List<com.teammovil.domain.Deworming>): List<DewormingView>{
        val dewormingList = listOf<DewormingView>().toMutableList()
        origin.forEachIndexed { index, deworming ->  dewormingList.add(
            map(deworming, index))
        }
        return dewormingList
    }

    fun map (deworming: com.teammovil.domain.Deworming, position: Int): DewormingView{
        return DewormingView(
            position,
            deworming.id,
            deworming.name,
            getStringFromDate(deworming.applicationDate) ?: ""
        )
    }

    fun mapPet (origin: PetView): com.teammovil.domain.Pet {
        return com.teammovil.domain.Pet(
            origin.id,
            origin.name.value ?: "",
            trueValueOfGenderType(origin.gender.value),
            origin.race.value ?: "",
            origin.description.value ?: "",
            getDateFromString(origin.approximateDateOfBirth.value),
            getDateFromString(origin.rescueDate.value),
            trueValueOfPetType(origin.petType.value),
            origin.sterilized.value,
            origin.vaccines.value ?: listOf(),
            origin.dewormings.value ?: listOf(),
            origin.mainPhoto.value ?: "",
            origin.status.value,
            origin.evidences.value ?: listOf()
        )
    }

    fun trueValueOfGenderType(data: String?) : GenderType{
        return try {
            GenderType.valueOf(
                data ?: GenderType.UNKNOWN.name
            )
        }catch (e: IllegalArgumentException){
            GenderType.UNKNOWN
        }
    }

    fun trueValueOfPetType(data: String?) : PetType{
        return try {
            PetType.valueOf(
                data ?: PetType.UNKNOWN.name
            )
        }catch (e: IllegalArgumentException){
            PetType.UNKNOWN
        }
    }

    fun mapPet (origin: com.teammovil.domain.Pet): PetView{
        return PetView(
            origin.id,
            FieldView(origin.name),
            SelectFieldView(
                origin.gender.name,
                origin.gender.ordinal
            ),
            FieldView(origin.race),
            FieldView(origin.description),
            FieldView(getStringFromDate(origin.approximateDateOfBirth)),
            FieldView(getStringFromDate(origin.rescueDate)),
            SelectFieldView(
                origin.petType.name,
                origin.petType.ordinal
            ),
            FieldView(origin.sterilized),
            FieldView(origin.vaccines),
            FieldView(origin.dewormings),
            FieldView(origin.mainPhoto),
            FieldView(origin.status),
            FieldView(origin.evidences)
        )
    }

    fun map (pet: PetView, errorList: List<Error>) : PetView{
        for(error in errorList){
            when (error.code){
                RulesErrors.NAME_FIELD_EMPTY_ERROR -> {
                    pet.name.valid = false
                    pet.name.messageResourceId = R.string.error_field_required
                }
                RulesErrors.DESCRIPTION_FIELD_EMPTY_ERROR -> {
                    pet.description.valid = false
                    pet.description.messageResourceId = R.string.error_field_required
                }
                RulesErrors.RACE_FIELD_EMPTY_ERROR -> {
                    pet.race.valid = false
                    pet.race.messageResourceId = R.string.error_field_required
                }
                RulesErrors.GENDER_FIELD_EMPTY_ERROR -> {
                    pet.gender.valid = false
                    pet.gender.messageResourceId = R.string.prompt_select_option
                }
                RulesErrors.TYPE_FIELD_EMPTY_ERROR -> {
                    pet.petType.valid = false
                    pet.petType.messageResourceId = R.string.prompt_select_option
                }
                RulesErrors.BIRTH_DATE_FIELD_EMPTY_ERROR -> {
                    pet.approximateDateOfBirth.valid = false
                    pet.approximateDateOfBirth.messageResourceId = R.string.error_field_required
                }
                RulesErrors.RESCUE_DATE_FIELD_EMPTY_ERROR -> {
                    pet.rescueDate.valid = false
                    pet.rescueDate.messageResourceId = R.string.error_field_required
                }
                RulesErrors.MAIN_PHOTO_FIELD_EMPTY_ERROR -> {
                    pet.mainPhoto.valid = false
                    pet.mainPhoto.messageResourceId = R.string.error_photo_required
                }
            }
        }
        return pet
    }

    fun map(rescuer: RescuerView, errorList: List<Error>): RescuerView {
        for(error in errorList) {
            when (error.code) {
                RulesErrors.NAME_FIELD_EMPTY_ERROR -> {
                    rescuer.name.valid = false
                    rescuer.name.messageResourceId = R.string.error_field_required
                }

                RulesErrors.ACTIVITY_START_DATE_FIELD_EMPTY_ERROR -> {
                    rescuer.activityStartDate.valid = false
                    rescuer.activityStartDate.messageResourceId = R.string.error_field_required
                }

                RulesErrors.EMAIL_FIELD_EMPTY_ERROR -> {
                    rescuer.email.valid = false
                    rescuer.email.messageResourceId = R.string.error_field_required
                }

                RulesErrors.PASSWORD_FIELD_EMPTY_ERROR -> {
                    rescuer.password.valid = false
                    rescuer.password.messageResourceId = R.string.error_field_required
                }

                RulesErrors.PHONE_FIELD_EMPTY_ERROR -> {
                    rescuer.phone.valid = false
                    rescuer.phone.messageResourceId = R.string.error_field_required
                }

                RulesErrors.ADDRESS_FIELD_EMPTY_ERROR -> {
                    rescuer.address.valid = false
                    rescuer.address.messageResourceId = R.string.error_field_required
                }

                RulesErrors.DESCRIPTION_FIELD_EMPTY_ERROR -> {
                    rescuer.descripion.valid = false
                    rescuer.descripion.messageResourceId = R.string.error_field_required
                }
            }
        }

        return rescuer
    }

    fun map (origin: RescuerView): com.teammovil.domain.Rescuer {
        return com.teammovil.domain.Rescuer(
            origin.id ?: "",
            origin.name.value ?: "",
            origin.descripion.value ?: "",
            origin.address.value ?: "",
            origin.email.value ?: "",
            origin.password.value ?: "",
            origin.phone.value ?: "",
            getDateFromString(origin.activityStartDate.value)
        )
    }


    fun map(adopter: AdopterView,errorList: List<Error>):AdopterView{
        for (error in errorList){
            when(error.code){
                RulesErrors.NAME_FIELD_EMPTY_ERROR ->{
                    adopter.name.valid = false
                    adopter.name.messageResourceId = R.string.error_field_required
                }

                RulesErrors.FIRST_LAST_NAME_FIELD_EMPTY_ERROR->{
                    adopter.firstLastName.valid = false
                    adopter.firstLastName.messageResourceId = R.string.error_field_required
                }

                RulesErrors.SECOND_LAST_NAME_FIELD_EMPTY_ERROR ->{
                    adopter.secondLastName.valid = false
                    adopter.secondLastName.messageResourceId = R.string.error_field_required
                }

                RulesErrors.GENDER_FIELD_EMPTY_ERROR->{
                    adopter.genderType.valid = false
                    adopter.genderType.messageResourceId = R.string.prompt_select_option
                }

                RulesErrors.BIRTH_DATE_FIELD_EMPTY_ERROR ->{
                    adopter.birthDay.valid = false
                    adopter.birthDay.messageResourceId = R.string.error_field_required
                }

                RulesErrors.MAIL_FIELD_EMPTY_ERROR ->{
                    adopter.email.valid = false
                    adopter.email.messageResourceId = R.string.error_field_required
                }

                RulesErrors.PASSWORD_FIELD_EMPTY_ERROR ->{
                    adopter.password.valid = false
                    adopter.password.messageResourceId = R.string.error_field_required
                }

                RulesErrors.PHONE_FIELD_EMPTY_ERROR ->{
                    adopter.phone.valid = false
                    adopter.phone.messageResourceId = R.string.error_field_required
                }

                RulesErrors.ADDRESS_FIELD_EMPTY_ERROR ->{
                    adopter.address.valid = false
                    adopter.address.messageResourceId = R.string.error_field_required
                }
            }
        }
        return adopter
    }

    fun map(origin: AdopterView): Adopter {
        return Adopter(
            origin.email.value?:"",
            origin.name.value?:"",
            origin.firstLastName.value?:"",
            origin.secondLastName.value?:"",
            trueValueOfGenderType(origin.genderType.value),
            getDateFromString(origin.birthDay.value),
            origin.password.value?:"",
            origin.phone.value?:"",
            origin.address.value?:""
        )
    }

    fun map (origin: EvidenceView): com.teammovil.domain.Evidence {
        return com.teammovil.domain.Evidence(
            origin.externalId,
            origin.comments.value!!,
            origin.photo.value ?: "",
            getDateFromString(origin.evidenceDate.value)
        )
    }
    fun map (evidence: EvidenceView, errorList: List<Error>) : EvidenceView {
        for (error in errorList) {
            when (error.code) {
                RulesErrors.EVIDENCE_PHOTO_FIELD_EMPTY_ERROR -> {
                    evidence.photo.valid = false
                    evidence.photo.messageResourceId = R.string.error_photo_required
                }
                RulesErrors.EVIDENCE_DATE_FIELD_EMPTY_ERROR -> {
                    evidence.evidenceDate.valid = false
                    evidence.evidenceDate.messageResourceId = R.string.error_field_required
                }
                RulesErrors.EVIDENCE_COMMENTS_FIELD_EMPTY_ERROR -> {
                    evidence.comments.valid = false
                    evidence.comments.messageResourceId = R.string.error_field_required
                }
            }
        }
        return evidence
    }

    fun map(user: UserView, errorList: List<Error>): UserView{
        for (error in errorList){
            when(error.code){
                RulesErrors.EMAIL_FIELD_EMPTY_ERROR -> {
                    user.email.valid = false

                }
                RulesErrors.PASSWORD_FIELD_EMPTY_ERROR -> {
                    user.password.valid = false

                }
            }
        }
        return user
    }

}
