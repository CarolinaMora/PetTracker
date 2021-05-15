package com.teammovil.pettracker.ui.common

import com.teammovil.pettracker.getDateFromString
import com.teammovil.pettracker.getStringFromDate
import com.teammovil.pettracker.ui.dewormings.DewormingView
import com.teammovil.pettracker.ui.sendevidence.EvidenceView
import com.teammovil.pettracker.ui.vaccines.VaccineView
import com.teammovil.domain.Error
import com.teammovil.domain.rules.RulesErrors
import com.teammovil.pettracker.R


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
            com.teammovil.domain.GenderType.valueOf(
                origin.gender.value ?: com.teammovil.domain.GenderType.MALE.name
            ),
            origin.race.value ?: "",
            origin.description.value ?: "",
            getDateFromString(origin.approximateDateOfBirth.value ?: "") ?: Date(),
            getDateFromString(origin.rescueDate.value ?: "") ?: Date(),
            com.teammovil.domain.PetType.valueOf(
                origin.petType.value ?: com.teammovil.domain.PetType.DOG.name
            ),
            origin.sterilized.value,
            origin.vaccines.value ?: listOf(),
            origin.dewormings.value ?: listOf(),
            origin.mainPhoto.value ?: "",
            origin.status.value,
            origin.evidences.value ?: listOf()
        )
    }

    fun mapPet (origin: com.teammovil.domain.Pet): PetView{
        return PetView(
            "",
            FieldView(origin.name),
            SelectFieldView(
                origin.gender.name,
                origin.gender.ordinal+1
            ),
            FieldView(origin.race),
            FieldView(origin.description),
            FieldView(getStringFromDate(origin.approximateDateOfBirth)),
            FieldView(getStringFromDate(origin.rescueDate)),
            SelectFieldView(
                origin.petType.name,
                origin.petType.ordinal+1
            ),
            FieldView(origin.sterilized),
            FieldView(origin.vaccines),
            FieldView(origin.dewormings),
            FieldView(origin.mainPhoto),
            FieldView(origin.status),
            FieldView(origin.evidences)
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
}