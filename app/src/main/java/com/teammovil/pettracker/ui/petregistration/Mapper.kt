package com.teammovil.pettracker.ui.petregistration

import com.teammovil.pettracker.domain.Vaccine
import com.teammovil.pettracker.getDateFromString
import com.teammovil.pettracker.ui.dewormings.DewormingView
import com.teammovil.pettracker.ui.vaccines.VaccineView
import java.util.*

object Mapper {

    fun mapVaccineList (vaccineViewList: List<VaccineView>): List<Vaccine>{
        return vaccineViewList.map {
            mapVaccine(it)
        }
    }

    fun mapVaccine (vaccineView: VaccineView): Vaccine{
        return Vaccine(
            name = vaccineView.name,
            applicationDate = getDateFromString(vaccineView.applicationDate)?.let{it}?: Date()
        )
    }

    fun mapDewormingList (dewormingViewList: List<DewormingView>): List<Date>{
        return dewormingViewList.map {
            getDateFromString(it.applicationDate)?.let{it} ?: Date()
        }
    }
}