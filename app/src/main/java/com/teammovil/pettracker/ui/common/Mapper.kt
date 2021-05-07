package com.teammovil.pettracker.ui.common

import com.teammovil.domain.rules.RulesErrors
import com.teammovil.pettracker.R
import com.teammovil.pettracker.getDateFromString
import com.teammovil.pettracker.getStringFromDate
import com.teammovil.pettracker.ui.dewormings.DewormingView
import com.teammovil.pettracker.ui.vaccines.VaccineView
import com.teammovil.domain.Error
import com.teammovil.domain.GenderType
import com.teammovil.domain.PetType
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
            applicationDate = getDateFromString(vaccineView.applicationDate)?.let { it } ?: Date()
        )
    }

    fun mapDewormingList (dewormingViewList: List<DewormingView>): List<com.teammovil.domain.Deworming>{
        return dewormingViewList.map {
            com.teammovil.domain.Deworming(
                it.idExternal,
                it.name,
                getDateFromString(it.applicationDate)?.let { it } ?: Date())
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
}