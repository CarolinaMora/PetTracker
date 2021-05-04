package com.teammovil.pettracker.ui.common

import com.teammovil.pettracker.domain.*
import com.teammovil.pettracker.getDateFromString
import com.teammovil.pettracker.getStringFromDate
import com.teammovil.pettracker.ui.dewormings.DewormingView
import com.teammovil.pettracker.ui.vaccines.VaccineView
import java.util.*

object Mapper {

    fun mapVaccineList (vaccineViewList: List<VaccineView>): List<Vaccine>{
        return vaccineViewList.map {
            mapVaccine(it)
        }
    }

    fun mapVaccineViewList (origin: List<Vaccine>): List<VaccineView>{
        val vaccineList = listOf<VaccineView>().toMutableList()
        origin.forEachIndexed { index, vaccine ->  vaccineList.add(
            map(vaccine, index))
        }
        return vaccineList
    }

    fun map (origin: Vaccine, position: Int): VaccineView{
        return VaccineView(
            position,
            origin.id,
            origin.name,
            getStringFromDate(origin.applicationDate ) ?: ""
        )
    }

    fun mapVaccine (vaccineView: VaccineView): Vaccine{
        return Vaccine(
            id = vaccineView.idExternal,
            name = vaccineView.name,
            applicationDate = getDateFromString(vaccineView.applicationDate)?.let{it}?: Date()
        )
    }

    fun mapDewormingList (dewormingViewList: List<DewormingView>): List<Deworming>{
        return dewormingViewList.map {
            Deworming(it.idExternal, it.name, getDateFromString(it.applicationDate)?.let{it} ?: Date())
        }
    }

    fun mapDewormingViewList (origin: List<Deworming>): List<DewormingView>{
        val dewormingList = listOf<DewormingView>().toMutableList()
        origin.forEachIndexed { index, deworming ->  dewormingList.add(
            map(deworming, index))
        }
        return dewormingList
    }

    fun map (deworming: Deworming, position: Int): DewormingView{
        return DewormingView(
            position,
            deworming.id,
            deworming.name,
            getStringFromDate(deworming.applicationDate) ?: ""
        )
    }

    fun mapPet (origin: PetView): Pet {
        return Pet(
            origin.id,
            origin.name.value?:"",
            GenderType.valueOf(origin.gender.value?:GenderType.MALE.name),
            origin.race.value?:"",
            origin.description.value?:"",
            getDateFromString(origin.approximateDateOfBirth.value?:"")?:Date(),
            getDateFromString(origin.rescueDate.value?:"")?: Date(),
            PetType.valueOf(origin.petType.value?:PetType.DOG.name),
            origin.sterilized.value,
            origin.vaccines.value ?: listOf(),
            origin.dewormings.value ?: listOf(),
            origin.mainPhoto.value ?: "",
            origin.status.value,
            origin.evidences.value ?: listOf()
        )
    }

    fun mapPet (origin: Pet): PetView{
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
}